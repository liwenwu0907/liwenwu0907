/*
 * Copyright 1999-2018 Alibaba Group Holding Ltd.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.example.demo.util.sentinel;

/**
 * @author Eric Zhao
 */
public final class SentinelConstants {

    public static final String FLOW_POSTFIX = "-flow-rules";
    public static final String GATEWAY_FLOW_POSTFIX = "-gateway-flow-rules";
    public static final String GATEWAY_API_POSTFIX = "-api-definition-rules";
    public static final String PARAM_FLOW_POSTFIX = "-param-rules";
    public static final String SERVER_NAMESPACE_SET_POSTFIX = "-cs-namespace-set";
    public static final String CLIENT_CONFIG_POSTFIX = "-cc-config";
    public static final String CLUSTER_MAP_POSTFIX = "-cluster-map";

    private SentinelConstants() {}
}
