//
//  BaseCallHandler.swift
//  delta_chat_core
//
//  Created by Cihan Oezkan on 12.08.19.
//

import Foundation


extension CallHandler {
    
    public func handleBaseCalls(methodCall: FlutterMethodCall, result: FlutterResult) {
        switch (methodCall.method) {
        case Method.Base.INIT:
            baseInit(result: result);
            break;
        case Method.Base.SYSTEM_INFO:
            systemInfo(result: result);
            break;
        case Method.Base.CORE_LISTENER:
            coreListener(methodCall: methodCall, result: result);
            break;
        case Method.Base.SET_CORE_STRINGS:
            setCoreStrings(methodCall: methodCall, result: result);
            break;
        default:
            print("Base: Failing for \(methodCall.method)")
            _ = FlutterMethodNotImplemented
        }
    }
    
    private func baseInit(result : FlutterResult) {
        result(open())
    }
    
    func dbfile() -> String {
        let paths = NSSearchPathForDirectoriesInDomains(.libraryDirectory, .userDomainMask, true)
        let documentsPath = paths[0]
        
        return documentsPath + "/messenger.db"
    }
    // TODO : Schauen was hier passiert und methode selbsterklärend benennen
    func open() -> Int32 {
        if mailboxPointer == nil {
            mailboxPointer = dc_context_new(nil, nil, "iOS")
            guard mailboxPointer != nil else {
                fatalError("Error: dc_context_new returned nil")
            }
        }
        let open = dc_open(mailboxPointer, dbfile(), nil)
        return open
    }
    
    private func coreListener(methodCall: FlutterMethodCall, result: FlutterResult) {
        //        Boolean add = methodCall.argument(ARGUMENT_ADD);
        //        Integer eventId = methodCall.argument(ARGUMENT_EVENT_ID);
        //        Integer listenerId = methodCall.argument(ARGUMENT_LISTENER_ID);
        //        if (eventId == null || add == null) {
        //        return;
        //        }
        //        if (add) {
        //        int newListenerId = eventChannelHandler.addListener(eventId);
        //        result.success(newListenerId);
        //        } else {
        //        if (listenerId == null) {
        //        return;
        //        }
        //        eventChannelHandler.removeListener(listenerId);
        //        result.success(null);
        //        }
        result(nil)
    }
    
    private func systemInfo(result: FlutterResult) {
        result("success")
    }
    
    private func setCoreStrings(methodCall: FlutterMethodCall, result: FlutterResult) {
        // TODO: Ask Daniel for NativeInteractionManager
//        guard let coreStrings = methodCall.arguments else {
//            return
//        }
//
//    Map<Long, String> coreStrings = methodCall.arguments();
//    nativeInteractionManager.setCoreStrings(coreStrings);
//    result.success(null);
        result(nil)
    }
    
    
    
}
