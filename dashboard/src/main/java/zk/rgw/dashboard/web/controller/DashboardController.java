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
package zk.rgw.dashboard.web.controller;

import reactor.core.publisher.Mono;

import zk.rgw.dashboard.framework.annotation.Controller;
import zk.rgw.dashboard.framework.annotation.RequestMapping;
import zk.rgw.dashboard.framework.annotation.RequestParam;
import zk.rgw.dashboard.framework.xo.AccessLogStatisticsArchiveLevel;
import zk.rgw.dashboard.web.bean.vo.dashboard.ApiCallsCount;
import zk.rgw.dashboard.web.service.DashboardService;
import zk.rgw.dashboard.web.service.factory.ServiceFactory;

@Controller("dashboard")
public class DashboardController {

    private final DashboardService dashboardService = ServiceFactory.get(DashboardService.class);

    @RequestMapping(path = "/api-calls-count")
    public Mono<ApiCallsCount> apiCallsCount(
            @RequestParam(name = "envId") String envId,
            @RequestParam(name = "orgId", required = false) String orgId
    ) {
        return dashboardService.apiCallsCount(envId, orgId);
    }

    @RequestMapping(path = "/access-log-archive", method = RequestMapping.Method.POST)
    public Mono<Void> archiveAccessLog(
            @RequestParam(name = "envId") String envId,
            @RequestParam(name = "minTimestamp") long minTimestamp,
            @RequestParam(name = "maxTimestamp") long maxTimestamp,
            @RequestParam(name = "level", required = false, defaultValue = "DAYS") AccessLogStatisticsArchiveLevel level
    ) {
        if (minTimestamp >= maxTimestamp) {
            return Mono.empty();
        }
        return dashboardService.archiveAccessLog(envId, minTimestamp, maxTimestamp, level);
    }

}
