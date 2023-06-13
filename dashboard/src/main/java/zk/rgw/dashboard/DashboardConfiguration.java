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

package zk.rgw.dashboard;

import lombok.Getter;
import lombok.Setter;
import picocli.CommandLine;

import zk.rgw.http.conf.ServerConfiguration;

// @formatter:off
@CommandLine.Command(
        name = "reactor-dashboard",
        version = "0.0.1",
        mixinStandardHelpOptions = true,
        description = "A http server manage routes for reactor-gateway.",
        usageHelpAutoWidth = true,
        showDefaultValues = true
)
// @formatter:on
public class DashboardConfiguration extends ServerConfiguration {

    private static final String DEFAULT_CONF_FILE = "conf/rgw-dashboard.properties";

    private static final int DEFAULT_PORT = 7000;

    @Getter
    @Setter
    @CommandLine.Option(names = "--jwt.hmac256.secret", description = "The secret key for hmac256 algorithm, if not specified, a random string is used.")
    private String jwtHmac256Secret;

    @Getter
    @Setter
    @CommandLine.Option(names = "--api.context.path", description = "The api context path.")
    private String apiContextPath = "/api";

    public DashboardConfiguration() {
        this.confFile = DEFAULT_CONF_FILE;
        this.serverPort = DEFAULT_PORT;
    }

}
