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

import reactor.core.publisher.Mono;

import zk.rgw.dashboard.web.bean.PageData;
import zk.rgw.dashboard.web.bean.vo.AccessLogVo;

public interface AccessLogService {

    @SuppressWarnings("java:S107")
    Mono<PageData<AccessLogVo>> searchAccessLogs(
            boolean asSubscriber,
            String envId, int pageNum, int pageSize,
            String requestId, String apiNameOrId, String clientIp, String clientNameOrId,
            Integer[] responseStatuses, Integer minTimeCostMillis, Long minTimeMillis, Long maxTimeMillis
    );

}
