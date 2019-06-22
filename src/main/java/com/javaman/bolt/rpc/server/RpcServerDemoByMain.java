/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.javaman.bolt.rpc.server;

import com.alipay.remoting.ConnectionEventType;
import com.alipay.remoting.exception.RemotingException;
import com.javaman.bolt.rpc.RequestBody;
import lombok.Data;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * a demo for rpc server, you can just run the main method to start a server
 *
 * @author tsui
 * @version $Id: RpcServerDemoByMain.java, v 0.1 2018-04-10 10:37 tsui Exp $
 */
@Data
public class RpcServerDemoByMain {

    static Logger logger = LoggerFactory.getLogger(RpcServerDemoByMain.class);

   static BoltServer server;
    int port = 8999;
    SimpleServerUserProcessor serverUserProcessor = new SimpleServerUserProcessor();

    AsyncServerUserProcessor asyncServerUserProcessor = new AsyncServerUserProcessor();

    ServerCONNECTEventProcessor serverConnectProcessor = new ServerCONNECTEventProcessor();
    ServerDISCONNECTEventProcessor serverDisConnectProcessor = new ServerDISCONNECTEventProcessor();

    public RpcServerDemoByMain() throws InterruptedException, RemotingException {
        // 1. create a Rpc server with port assigned
        server = new BoltServer(port,true);
        // 2. add processor for connect and close event if you need
        server.addConnectionEventProcessor(ConnectionEventType.CONNECT, serverConnectProcessor);
        server.addConnectionEventProcessor(ConnectionEventType.CLOSE, serverDisConnectProcessor);
        // 3. register user processor for client request
        server.registerUserProcessor(asyncServerUserProcessor);



        // 4. server start
        if (server.start()) {
            logger.info("---------------------start ok---------------------");
        } else {
            logger.error("start failed");
        }
        // server.getRpcServer().stop();
    }

    public static void main(String[] args) throws RemotingException, InterruptedException {
        new RpcServerDemoByMain();
    }
}
