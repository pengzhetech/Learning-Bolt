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
package com.javaman.bolt.rpc;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.IntStream;

/**
 * biz request as a demo
 *
 * @author jiangping
 * @version $Id: RequestBody.java, v 0.1 2015-10-19 PM2:32:26 tao Exp $
 */
@Data
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class RequestBody implements Serializable {

    public static final String DEFAULT_CLIENT_STR = "HELLO WORLD! I'm from client";
    public static final String DEFAULT_SERVER_STR = "HELLO WORLD! I'm from server";
    public static final String DEFAULT_SERVER_RETURN_STR = "HELLO WORLD! I'm server return";
    public static final String DEFAULT_CLIENT_RETURN_STR = "HELLO WORLD! I'm client return";
    public static final String DEFAULT_ONEWAY_STR = "HELLO WORLD! I'm oneway req";
    public static final String DEFAULT_SYNC_STR = "HELLO WORLD! I'm sync req";
    public static final String DEFAULT_FUTURE_STR = "HELLO WORLD! I'm future req";
    public static final String DEFAULT_CALLBACK_STR = "HELLO WORLD! I'm call back req";
    /**
     * for serialization
     */
    private static final long serialVersionUID = -1288207208017808618L;
    /**
     * id
     */
    private int id;
    /**
     * msg
     */
    private String msg;
    /**
     * body
     */
    private byte[] body;

    private Random r = new Random();

    public RequestBody(int id, String msg) {
        this.id = id;
        this.msg = msg;
    }

    public static void main(String[] args) {

        Map<String, String> map = new HashMap<>();
        IntStream.rangeClosed(0, 10).forEach(index -> {
            map.put("key", String.valueOf(index));
        });
        System.out.println(map);


        Map<String, String> cuMap = new ConcurrentHashMap<>();
        IntStream.rangeClosed(0, 10).forEach(index -> {
            cuMap.putIfAbsent("key", String.valueOf(index));
        });
        System.out.println(cuMap);

        System.out.println(null==null);


    }

    static public enum InvokeType {
        ONEWAY, SYNC, FUTURE, CALLBACK;
    }
}