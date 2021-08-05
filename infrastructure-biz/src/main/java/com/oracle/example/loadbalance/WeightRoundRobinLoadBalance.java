package com.oracle.example.loadbalance;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
@Slf4j
public class WeightRoundRobinLoadBalance implements LoadBalance {

    @Override
    public ServerNode select(List<ServerNode> serverNodes) {

        ServerNode serverNodeSelected = null;

        int maxWeight = Integer.MIN_VALUE;

        int totalWeight = 0;

        for (ServerNode serverNode : serverNodes) {

            // 增加各自的 currentWeight
            serverNode.increaseCurrentWeight();

            if (serverNode.getCurrentWeight() > maxWeight) {

                maxWeight = serverNode.getCurrentWeight();

                serverNodeSelected = serverNode;
            }
            totalWeight += serverNode.getWeight();
        }

        if (serverNodeSelected != null) {

            // 被选中的节点，currentWeight 需要减去所有 weight 之和
            serverNodeSelected.selected(totalWeight);
            return serverNodeSelected;
        }
        // SHOULD NOT HAPPEN HERE
        return serverNodes.get(0);
    }


    public static void main(String[] args) {
        WeightRoundRobinLoadBalance lb = new WeightRoundRobinLoadBalance();
        List<ServerNode> servers = new ArrayList<>();
        // 初始化3个节点：a、b、c，权重分别是：1、2、4

        servers.add(new ServerNode("a", 1, 0));

        servers.add(new ServerNode("b", 2, 0));
        servers.add(new ServerNode("c", 4, 0));
        for (int i = 0; i < 15; i++) {

            if (i % 7 == 0) {
                log.info(String.format("\n第 %s 轮请求======:", (i / 7) + 1));
            }
            ServerNode selected = lb.select(servers);
            log.info(String.format("第 %s 次请求，选中机器：%s", i + 1, selected.getNodeName()));


        }
    }
}
