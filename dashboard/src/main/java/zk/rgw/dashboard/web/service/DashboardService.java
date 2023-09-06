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
package zk.rgw.dashboard.web.service;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import zk.rgw.dashboard.web.bean.AccessLogStatistics;
import zk.rgw.dashboard.web.bean.AccessLogStatisticsWithTime;
import zk.rgw.dashboard.web.bean.TimeRangeType;

public interface DashboardService {

    Mono<Long> apisCount(String envId, String orgId);

    Flux<AccessLogStatisticsWithTime> apiCallsCountTrend(String envId, String orgId, String apiId, TimeRangeType timeRangeType);

    Mono<AccessLogStatistics> apiCallsCount(String envId, String orgId, String apiId);

}
