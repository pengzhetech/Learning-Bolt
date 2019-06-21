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

import com.alipay.remoting.Connection;
import com.alipay.remoting.ConnectionEventProcessor;
import lombok.Getter;
import lombok.Setter;
import org.junit.Assert;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * ConnectionEventProcessor for ConnectionEventType.CONNECT
 *
 * @author xiaomin.cxm
 * @version $Id: ServerCONNECTEventProcessor.java, v 0.1 Apr 8, 2016 10:58:48 AM xiaomin.cxm Exp $
 */
@Getter
@Setter
public class ServerCONNECTEventProcessor implements ConnectionEventProcessor {

    static Logger logger = LoggerFactory.getLogger(ServerCONNECTEventProcessor.class);

    private AtomicBoolean connected = new AtomicBoolean();
    private AtomicInteger connectTimes = new AtomicInteger();
    private Connection connection;
    private String remoteAddr;
    private CountDownLatch latch = new CountDownLatch(1);

    @Override
    public void onEvent(String remoteAddr, Connection conn) {
        logger.info("ServerCONNECTEventProcessor connected 服务端与客户端连接上了-----哦也");
        Assert.assertNotNull(remoteAddr);
        doCheckConnection(conn);
        this.remoteAddr = remoteAddr;
        this.connection = conn;
        connected.set(true);
        connectTimes.incrementAndGet();
        latch.countDown();
    }

    /**
     * do check connection
     *
     * @param conn
     */
    private void doCheckConnection(Connection conn) {
        Assert.assertNotNull(conn);
        Assert.assertNotNull(conn.getPoolKeys());
        Assert.assertTrue(conn.getPoolKeys().size() > 0);
        Assert.assertNotNull(conn.getChannel());
        Assert.assertNotNull(conn.getUrl());
        Assert.assertNotNull(conn.getChannel().attr(Connection.CONNECTION).get());
    }

    public void reset() {
        this.connectTimes.set(0);
        this.connected.set(false);
        this.connection = null;
    }
}
