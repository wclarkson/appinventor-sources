// -*- mode: java; c-basic-offset: 2; -*-
// Copyright 2009-2011 Google, All Rights reserved
// Copyright 2011-2012 MIT, All rights reserved
// Released under the MIT License https://raw.github.com/mit-cml/app-inventor/master/mitlicense.txt

package com.google.appinventor.components.runtime;

import com.google.appinventor.components.annotations.DesignerComponent;
import com.google.appinventor.components.annotations.SimpleFunction;
import com.google.appinventor.components.annotations.SimpleObject;
import com.google.appinventor.components.annotations.UsesLibraries;
import com.google.appinventor.components.annotations.UsesPermissions;
import com.google.appinventor.components.common.ComponentCategory;
import com.google.appinventor.components.common.YaVersion;
import com.google.appinventor.components.runtime.util.ErrorMessages;
import com.google.appinventor.components.runtime.util.MediaUtil;
import com.google.appinventor.components.runtime.util.YailList;

import android.util.Log;

import org.java_websocket.client.WebSocketClient;
import org.java_websocket.drafts.Draft;
import org.java_websocket.drafts.Draft_10;
import org.java_websocket.handshake.ServerHandshake;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import java.net.URI;
import java.util.HashMap;


@DesignerComponent(version = 0,
        description = "Description",
        category = ComponentCategory.CONNECTIVITY,
        nonVisible = true,
        iconName = "icon")
@SimpleObject
@UsesPermissions(permissionNames = "android.permission.INTERNET")
@UsesLibraries(libraries = "java_websocket.jar")
public class BlockyTalky extends AndroidNonvisibleComponent implements Component {
    private WebSocketClient client;
    private static String LOG_TAG = "BLOCKYTALKY";

    /**
     * Creates a new BlockyTalky component.
     */
    public BlockyTalky(ComponentContainer container) {
        super(container.$form());

        HashMap<String, String > protocol = new HashMap<String, String>(){{
            put("Sec-WebSocket-Protocol","echo-protocol");
        }};
        try {
            client = new EmptyClient(new URI("http://130.64.196.106:8080"), new Draft_10(), protocol, 10000);
            client.connect();
            int i=1;
            while(!client.isOpen()){
                i *= i;
                i ++;
            }
            Log.d(LOG_TAG, "Socket Set Up");
        } catch (Exception e) {
            Log.d(LOG_TAG, "Exception Caught");
            e.printStackTrace();
        }
    }

    // Test function
    @SimpleFunction(description = "Sends a message to the WebSocket.")
    public void SendMessage(String message) {
        client.send(message);
    }

    public class EmptyClient extends WebSocketClient {

        public EmptyClient(URI serverUri, Draft draft, HashMap<String, String > protocol, int timeout) {
            super(serverUri, draft, protocol, timeout);
        }
        public EmptyClient(URI serverUri, Draft draft) {
            super(serverUri, draft);
        }
        public EmptyClient(URI serverURI) {
            super(serverURI);
        }

        @Override
        public void onOpen(ServerHandshake handshakedata) {
            Log.d(LOG_TAG, "new connection opened");
        }

        @Override
        public void onClose(int code, String reason, boolean remote) {
            Log.d(LOG_TAG, "closed with exit code " + code + " additional info: " + reason);
        }

        @Override
        public void onMessage(String message) {
            Log.d(LOG_TAG, "received message: " + message);
        }

        @Override
        public void onError(Exception ex) {
            Log.d(LOG_TAG, "an error occured:" + ex);
        }
    }
}
