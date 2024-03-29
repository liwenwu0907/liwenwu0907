///*
// * Copyright 1999-2018 Alibaba Group Holding Ltd.
// *
// * Licensed under the Apache License, Version 2.0 (the "License");
// * you may not use this file except in compliance with the License.
// * You may obtain a copy of the License at
// *
// *      http://www.apache.org/licenses/LICENSE-2.0
// *
// * Unless required by applicable law or agreed to in writing, software
// * distributed under the License is distributed on an "AS IS" BASIS,
// * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
// * See the License for the specific language governing permissions and
// * limitations under the License.
// */
//package com.example.demo.util.sentinel;
//
//import com.alibaba.csp.sentinel.cluster.ClusterStateManager;
//import com.alibaba.csp.sentinel.cluster.client.config.ClusterClientAssignConfig;
//import com.alibaba.csp.sentinel.cluster.client.config.ClusterClientConfig;
//import com.alibaba.csp.sentinel.cluster.client.config.ClusterClientConfigManager;
//import com.alibaba.csp.sentinel.cluster.flow.rule.ClusterFlowRuleManager;
//import com.alibaba.csp.sentinel.cluster.flow.rule.ClusterParamFlowRuleManager;
//import com.alibaba.csp.sentinel.cluster.server.config.ClusterServerConfigManager;
//import com.alibaba.csp.sentinel.cluster.server.config.ServerTransportConfig;
//import com.alibaba.csp.sentinel.datasource.ReadableDataSource;
//import com.alibaba.csp.sentinel.datasource.apollo.ApolloDataSource;
//import com.alibaba.csp.sentinel.init.InitFunc;
//import com.alibaba.csp.sentinel.slots.block.flow.FlowRule;
//import com.alibaba.csp.sentinel.slots.block.flow.FlowRuleManager;
//import com.alibaba.csp.sentinel.slots.block.flow.param.ParamFlowRule;
//import com.alibaba.csp.sentinel.slots.block.flow.param.ParamFlowRuleManager;
//import com.alibaba.csp.sentinel.transport.config.TransportConfig;
//import com.alibaba.csp.sentinel.util.HostNameUtil;
//import com.alibaba.fastjson.JSON;
//import com.alibaba.fastjson.TypeReference;
//import org.springframework.stereotype.Component;
//
//import java.util.List;
//import java.util.Objects;
//import java.util.Optional;
//
///**
// * @author Eric Zhao
// */
//@Component
//public class ClusterInitFunc implements InitFunc {
//
//
//    private final String flowDataId = "api-server_sentinel" + SentinelConstants.FLOW_POSTFIX;
//    private final String paramDataId = "api-server_sentinel" + SentinelConstants.PARAM_FLOW_POSTFIX;
//    private final String configDataId = "api-server_sentinel" + "-cluster-client-config";
//    private final String clusterMapDataId = "api-server_sentinel" + SentinelConstants.CLUSTER_MAP_POSTFIX;
//
//
//    private final String namespaceName = "sentinel-config";
//
//    @Override
//    public void init() throws Exception {
//        // Register client dynamic rule data source.
//        initDynamicRuleProperty();
//
//        // Register token client related data source.
//        // Token client common config:
//        initClientConfigProperty();
//        // Token client assign config (e.g. target token server) retrieved from assign map:
//        initClientServerAssignProperty();
//
//        // Register token server related data source.
//        // Register dynamic rule data source supplier for token server:
//        registerClusterRuleSupplier();
//        // Token server transport config extracted from assign map:
//        initServerTransportConfigProperty();
//
//        // Init cluster state property for extracting mode from cluster map data source.
//        initStateProperty();
//    }
//
//    private void initDynamicRuleProperty() {
//        //客户端
//        String defaultRuleValue = null;
//        //读取流控规则
//        ReadableDataSource<String, List<FlowRule>> flowRuleDataSource = new ApolloDataSource<>(namespaceName,flowDataId,defaultRuleValue, source -> JSON.parseObject((String) source, new TypeReference<List<FlowRule>>() {}));
//        FlowRuleManager.register2Property(flowRuleDataSource.getProperty());
//        //读取参数流控规则
//        ReadableDataSource<String, List<ParamFlowRule>> paramFlowRuleDataSource = new ApolloDataSource<>(namespaceName,flowDataId,defaultRuleValue, source -> JSON.parseObject((String) source, new TypeReference<List<ParamFlowRule>>() {}));
//        ParamFlowRuleManager.register2Property(paramFlowRuleDataSource.getProperty());
//    }
//
//    private void initClientConfigProperty() {
//        //配置客户端超时时间
//        String defaultConfig = null;
//        ReadableDataSource<String, ClusterClientConfig> clientConfigDs = new ApolloDataSource<>(namespaceName,configDataId,defaultConfig, source -> JSON.parseObject((String) source, new TypeReference<ClusterClientConfig>() {}));
//        ClusterClientConfigManager.registerClientConfigProperty(clientConfigDs.getProperty());
//    }
//
//    private void initServerTransportConfigProperty() {
//        //配置初始化server和client的IP，port等
//        String defaultConfig = null;
//        ReadableDataSource<String, ServerTransportConfig> serverTransportDs = new ApolloDataSource<>(namespaceName, clusterMapDataId,
//                defaultConfig, source -> {
//            List<ClusterGroupEntity> groupList = JSON.parseObject(source, new TypeReference<List<ClusterGroupEntity>>() {});
//            return Optional.ofNullable(groupList)
//                .flatMap(this::extractServerTransportConfig)
//                .orElse(null);
//        });
//        ClusterServerConfigManager.registerServerTransportProperty(serverTransportDs.getProperty());
//    }
//
//    private void registerClusterRuleSupplier() {
//        //token server的限流规则
//        // Register cluster flow rule property supplier which creates data source by namespace.
//        // Flow rule dataId format: ${namespace}-flow-rules
//        String defaultRuleValue = null;
//        ClusterFlowRuleManager.setPropertySupplier(namespace -> {
//            ReadableDataSource<String, List<FlowRule>> ds = new ApolloDataSource<>(namespaceName,flowDataId,defaultRuleValue, source -> JSON.parseObject(source, new TypeReference<List<FlowRule>>() {}));
//            return ds.getProperty();
//        });
//        // Register cluster parameter flow rule property supplier which creates data source by namespace.
//        ClusterParamFlowRuleManager.setPropertySupplier(namespace -> {
//            ReadableDataSource<String, List<ParamFlowRule>> ds = new ApolloDataSource<>(namespaceName,flowDataId,
//                    defaultRuleValue, source -> JSON.parseObject(source, new TypeReference<List<ParamFlowRule>>() {}));
//            return ds.getProperty();
//        });
//    }
//
//    private void initClientServerAssignProperty() {
//        //分配client（clientSet）和server（ip，port，machineId）
//        // Cluster map format:
//        // [{"clientSet":["112.12.88.66@8729","112.12.88.67@8727"],"ip":"112.12.88.68","machineId":"112.12.88.68@8728","port":11111}]
//        // machineId: <ip@commandPort>, commandPort for port exposed to Sentinel dashboard (transport module)
//        String defaultConfig = null;
//        ReadableDataSource<String, ClusterClientAssignConfig> clientAssignDs = new ApolloDataSource<>(namespaceName, clusterMapDataId,
//                defaultConfig, source -> {
//            List<ClusterGroupEntity> groupList = JSON.parseObject(source, new TypeReference<List<ClusterGroupEntity>>() {});
//            return Optional.ofNullable(groupList)
//                .flatMap(this::extractClientAssignment)
//                .orElse(null);
//        });
//        ClusterClientConfigManager.registerServerAssignProperty(clientAssignDs.getProperty());
//    }
//
//    private void initStateProperty() {
//        // Cluster map format:
//        // [{"clientSet":["112.12.88.66@8729","112.12.88.67@8727"],"ip":"112.12.88.68","machineId":"112.12.88.68@8728","port":11111}]
//        // machineId: <ip@commandPort>, commandPort for port exposed to Sentinel dashboard (transport module)
//        String defaultConfig = null;
//        ReadableDataSource<String, Integer> clusterModeDs = new ApolloDataSource<>(namespaceName, clusterMapDataId,
//                defaultConfig, source -> {
//            List<ClusterGroupEntity> groupList = JSON.parseObject(source, new TypeReference<List<ClusterGroupEntity>>() {});
//            return Optional.ofNullable(groupList)
//                .map(this::extractMode)
//                .orElse(ClusterStateManager.CLUSTER_NOT_STARTED);
//        });
//        ClusterStateManager.registerProperty(clusterModeDs.getProperty());
//    }
//
//    private int extractMode(List<ClusterGroupEntity> groupList) {
//        // If any server group machineId matches current, then it's token server.
//        if (groupList.stream().anyMatch(this::machineEqual)) {
//            return ClusterStateManager.CLUSTER_SERVER;
//        }
//        // If current machine belongs to any of the token server group, then it's token client.
//        // Otherwise it's unassigned, should be set to NOT_STARTED.
//        boolean canBeClient = groupList.stream()
//            .flatMap(e -> e.getClientSet().stream())
//            .filter(Objects::nonNull)
//            .anyMatch(e -> e.equals(getCurrentMachineId()));
//        return canBeClient ? ClusterStateManager.CLUSTER_CLIENT : ClusterStateManager.CLUSTER_NOT_STARTED;
//    }
//
//    private Optional<ServerTransportConfig> extractServerTransportConfig(List<ClusterGroupEntity> groupList) {
//        return groupList.stream()
//            .filter(this::machineEqual)
//            .findAny()
//            .map(e -> new ServerTransportConfig().setPort(e.getPort()).setIdleSeconds(600));
//    }
//
//    private Optional<ClusterClientAssignConfig> extractClientAssignment(List<ClusterGroupEntity> groupList) {
//        if (groupList.stream().anyMatch(this::machineEqual)) {
//            return Optional.empty();
//        }
//        // Build client assign config from the client set of target server group.
//        for (ClusterGroupEntity group : groupList) {
//            if (group.getClientSet().contains(getCurrentMachineId())) {
//                String ip = group.getIp();
//                Integer port = group.getPort();
//                return Optional.of(new ClusterClientAssignConfig(ip, port));
//            }
//        }
//        return Optional.empty();
//    }
//
//    private boolean machineEqual(/*@Valid*/ ClusterGroupEntity group) {
//        return getCurrentMachineId().equals(group.getMachineId());
//    }
//
//    private String getCurrentMachineId() {
//        // Note: this may not work well for container-based env.
//        // TODO: 2022/4/19 这边的IP可能获取不正确
//        return HostNameUtil.getIp() + SEPARATOR + TransportConfig.getRuntimePort();
//    }
//
//    private static final String SEPARATOR = "@";
//}