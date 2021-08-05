package com.oracle.example.loadbalance;

import java.util.List;

public interface LoadBalance {

     ServerNode select(List<ServerNode> serverNodes);
}
