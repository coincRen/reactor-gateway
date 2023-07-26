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
package zk.rgw.dashboard.web.repository.factory;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import com.mongodb.reactivestreams.client.MongoClient;
import com.mongodb.reactivestreams.client.MongoDatabase;

import zk.rgw.dashboard.web.repository.OrganizationRepository;
import zk.rgw.dashboard.web.repository.UserRepository;

public class RepositoryFactory {

    private static final Map<Class<?>, Object> REPOSITORY_MAP = new HashMap<>();

    private RepositoryFactory() {
    }

    public static void init(MongoClient mongoClient, MongoDatabase mongoDatabase) {
        Objects.requireNonNull(mongoClient);
        Objects.requireNonNull(mongoDatabase);
        REPOSITORY_MAP.put(UserRepository.class, new UserRepository(mongoClient, mongoDatabase));
        REPOSITORY_MAP.put(OrganizationRepository.class, new OrganizationRepository(mongoClient, mongoDatabase));
    }

    @SuppressWarnings("unchecked")
    public static <R> R get(Class<R> repositoryClass) {
        return (R) REPOSITORY_MAP.get(repositoryClass);
    }

}
