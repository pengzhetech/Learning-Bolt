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
package com.javaman.bolt.rpc.client;

import com.alipay.remoting.ConnectionEventType;
import com.alipay.remoting.exception.RemotingException;
import com.alipay.remoting.rpc.RpcClient;
import com.alipay.remoting.rpc.RpcResponseFuture;
import com.javaman.bolt.rpc.RequestBody;
import org.junit.Assert;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author tsui
 * @version $Id: RpcClientDemoByMain.java, v 0.1 2018-04-10 10:39 tsui Exp $
 */
public class RpcClientDemoByMain {

    static Logger logger = LoggerFactory.getLogger(RpcClientDemoByMain.class);

    static RpcClient client;

    static String addr = "127.0.0.1:8999";

    SimpleClientUserProcessor clientUserProcessor = new SimpleClientUserProcessor();
    ClientCONNECTEventProcessor clientConnectProcessor = new ClientCONNECTEventProcessor();
    ClientDISCONNECTEventProcessor clientDisConnectProcessor = new ClientDISCONNECTEventProcessor();

    public RpcClientDemoByMain() {
        // 1. create a rpc client
        client = new RpcClient();
        // 2. add processor for connect and close event if you need
        client.addConnectionEventProcessor(ConnectionEventType.CONNECT, clientConnectProcessor);
        client.addConnectionEventProcessor(ConnectionEventType.CLOSE, clientDisConnectProcessor);
        client.registerUserProcessor(clientUserProcessor);
        // 3. do init
        client.init();
    }

    public static void main(String[] args) {
        new RpcClientDemoByMain();
        RequestBody req = new RequestBody(2, "hello world sync");
        try {
            RequestBody res = (RequestBody) client.invokeSync(addr, req, 80000);
       //     RpcResponseFuture rpcResponseFuture = client.invokeWithFuture(addr, req, 8000);

            logger.info("客户端调用结果----invoke sync result = [" + res + "]");
        } catch (RemotingException e) {
            String errMsg = "RemotingException caught in oneway!";
            logger.error(errMsg, e);
            Assert.fail(errMsg);
        } catch (InterruptedException e) {
            logger.error("interrupted!");
        }
        client.shutdown();
    }
}
