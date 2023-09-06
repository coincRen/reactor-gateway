/*
 * Copyright 2023 zoukang, All Rights Reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package zk.rgw.dashboard.web.repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import com.mongodb.client.model.Aggregates;
import com.mongodb.client.model.Filters;
import com.mongodb.client.model.Projections;
import com.mongodb.reactivestreams.client.MongoClient;
import com.mongodb.reactivestreams.client.MongoDatabase;
import org.bson.Document;
import org.bson.conversions.Bson;
import org.bson.types.ObjectId;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import zk.rgw.dashboard.web.bean.ApiPublishStatus;
import zk.rgw.dashboard.web.bean.RouteDefinitionPublishSnapshot;
import zk.rgw.dashboard.web.bean.entity.Api;

public class ApiRepository extends BaseAuditableEntityMongodbRepository<Api> {

    public ApiRepository(MongoClient mongoClient, MongoDatabase database) {
        super(mongoClient, database, Api.class);
    }

    public Mono<Boolean> existOneByNameAndOrg(String name, String orgId) {
        Bson filer = Filters.and(
                Filters.eq("name", name),
                Filters.eq("organization", new ObjectId(orgId))
        );
        return exists(filer);
    }

    public Mono<Boolean> belongsToOrg(String apiId, String orgId) {
        Bson filer = Filters.and(
                Filters.eq("_id", new ObjectId(apiId)),
                Filters.eq("organization", new ObjectId(orgId))
        );
        return exists(filer);
    }

    @Override
    public Mono<Api> findOneById(String id) {
        return super.findOneById(id).doOnNext(api -> {
            Map<String, RouteDefinitionPublishSnapshot> publishSnapshots = api.getPublishSnapshots();
            if (Objects.nonNull(publishSnapshots) && !publishSnapshots.isEmpty()) {
                for (RouteDefinitionPublishSnapshot publishSnapshot : publishSnapshots.values()) {
                    if (
                        ApiPublishStatus.PUBLISHED.equals(publishSnapshot.getPublishStatus())
                                && !Objects.equals(publishSnapshot.getRouteDefinition(), api.getRouteDefinition())
                    ) {
                        publishSnapshot.setPublishStatus(ApiPublishStatus.NOT_UPDATED);
                    }
                }
            }
        });
    }

    public Flux<String> getApiIdsByOrgId(String orgId) {
        List<Bson> aggPipeline = new ArrayList<>();
        aggPipeline.add(Aggregates.match(Filters.eq("organization", new ObjectId(orgId))));
        aggPipeline.add(Aggregates.project(Projections.include("_id")));
        return Flux.from(mongoCollection.withDocumentClass(Document.class).aggregate(aggPipeline)).map(doc -> doc.getObjectId("_id").toString());
    }

}
