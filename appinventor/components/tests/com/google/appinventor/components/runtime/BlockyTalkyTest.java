// -*- mode: java; c-basic-offset: 2; -*-
// Copyright 2009-2011 Google, All Rights reserved
// Copyright 2011-2012 MIT, All rights reserved
// Released under the MIT License https://raw.github.com/mit-cml/app-inventor/master/mitlicense.txt

package com.google.appinventor.components.runtime;

import com.google.appinventor.components.annotations.UsesLibraries;
import com.google.appinventor.components.runtime.util.ErrorMessages;
import com.google.appinventor.components.runtime.util.YailList;
import com.google.appinventor.components.runtime.BlockyTalky;


import org.java_websocket.client.WebSocketClient;
import org.java_websocket.drafts.Draft;
import org.java_websocket.drafts.Draft_10;

import junit.framework.TestCase;
import junit.framework.Assert;

@UsesLibraries(libraries = "java_websocket.jar")
public class BlockyTalkyTest extends TestCase {
    @Override
    protected void setUp() throws Exception {

    }

    public void testMessageToJson() {
        BlockyTalky bt = new BlockyTalky(null);
        BlockyTalky.BlockyTalkyMessage message = bt.new BlockyTalkyMessage("source", "destination", "content");
        String messageJson = "{\"py/object\": \"__main__.Message\", \"content\": \"content\", \"destination\": \"destination\", \"source\": \"source\"}";
        assertEquals(message.toJson(), messageJson);
    }
}
