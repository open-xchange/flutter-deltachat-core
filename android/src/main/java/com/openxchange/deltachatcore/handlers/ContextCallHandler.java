/*
 * OPEN-XCHANGE legal information
 *
 * All intellectual property rights in the Software are protected by
 * international copyright laws.
 *
 *
 * In some countries OX, OX Open-Xchange and open xchange
 * as well as the corresponding Logos OX Open-Xchange and OX are registered
 * trademarks of the OX Software GmbH group of companies.
 * The use of the Logos is not covered by the Mozilla Public License 2.0 (MPL 2.0).
 * Instead, you are allowed to use these Logos according to the terms and
 * conditions of the Creative Commons License, Version 2.5, Attribution,
 * Non-commercial, ShareAlike, and the interpretation of the term
 * Non-commercial applicable to the aforementioned license is published
 * on the web site https://www.open-xchange.com/terms-and-conditions/.
 *
 * Please make sure that third-party modules and libraries are used
 * according to their respective licenses.
 *
 * Any modifications to this package must retain all copyright notices
 * of the original copyright holder(s) for the original code used.
 *
 * After any such modifications, the original and derivative code shall remain
 * under the copyright of the copyright holder(s) and/or original author(s) as stated here:
 * https://www.open-xchange.com/legal/. The contributing author shall be
 * given Attribution for the derivative code and a license granting use.
 *
 * Copyright (C) 2016-2020 OX Software GmbH
 * Mail: info@open-xchange.com
 *
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 *
 * This program is distributed in the hope that it will be useful, but
 * WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY
 * or FITNESS FOR A PARTICULAR PURPOSE. See the Mozilla Public License 2.0
 * for more details.
 */

package com.openxchange.deltachatcore.handlers;

import com.b44t.messenger.DcChat;
import com.b44t.messenger.DcContact;
import com.b44t.messenger.DcContext;
import com.b44t.messenger.DcMsg;
import com.openxchange.deltachatcore.Cache;

import io.flutter.plugin.common.MethodCall;
import io.flutter.plugin.common.MethodChannel;

public class ContextCallHandler extends AbstractCallHandler {

    private static final String METHOD_CONFIG_SET = "context_configSet";
    private static final String METHOD_CONFIG_GET = "context_configGet";
    private static final String METHOD_CONFIG_GET_INT = "context_configGetInt";
    private static final String METHOD_CONFIGURE = "context_configure";
    private static final String METHOD_IS_CONFIGURED = "context_isConfigured";
    private static final String METHOD_ADD_ADDRESS_BOOK = "context_addAddressBook";
    private static final String METHOD_CREATE_CONTACT = "context_createContact";
    private static final String METHOD_DELETE_CONTACT = "context_deleteContact";
    private static final String METHOD_BLOCK_CONTACT = "context_blockContact";
    private static final String METHOD_CREATE_CHAT_BY_CONTACT_ID = "context_createChatByContactId";
    private static final String METHOD_CREATE_CHAT_BY_MESSAGE_ID = "context_createChatByMessageId";
    private static final String METHOD_CREATE_GROUP_CHAT = "context_createGroupChat";
    private static final String METHOD_GET_CONTACT = "context_getContact";
    private static final String METHOD_GET_CONTACTS = "context_getContacts";
    private static final String METHOD_GET_CHAT = "context_getChat";
    private static final String METHOD_GET_CHAT_MESSAGES = "context_getChatMessages";
    private static final String METHOD_CREATE_CHAT_MESSAGE = "context_createChatMessage";
    private static final String METHOD_ADD_CONTACT_TO_CHAT = "context_addContactToChat";

    private static final String TYPE_INT = "int";
    private static final String TYPE_STRING = "String";

    private static final int DC_MSG_UNDEFINED = 0;
    private static final int DC_MSG_TEXT = 10;
    private static final int DC_MSG_IMAGE = 20;
    private static final int DC_MSG_GIF = 21;
    private static final int DC_MSG_AUDIO = 40;
    private static final int DC_MSG_VOICE = 41;
    private static final int DC_MSG_VIDEO = 50;
    private static final int DC_MSG_FILE = 60;

    private final Cache<DcContact> contactCache;
    private final Cache<DcMsg> messageCache;
    private final Cache<DcChat> chatCache;

    public ContextCallHandler(DcContext dcContext, Cache<DcContact> contactCache, Cache<DcMsg> messageCache, Cache<DcChat> chatCache) {
        super(dcContext);
        this.contactCache = contactCache;
        this.messageCache = messageCache;
        this.chatCache = chatCache;
    }

    @Override
    public void handleCall(MethodCall methodCall, MethodChannel.Result result) {
        switch (methodCall.method) {
            case METHOD_CONFIG_SET:
                setConfig(methodCall, result);
                break;
            case METHOD_CONFIG_GET:
                getConfig(methodCall, result, TYPE_STRING);
                break;
            case METHOD_CONFIG_GET_INT:
                getConfig(methodCall, result, TYPE_INT);
                break;
            case METHOD_CONFIGURE:
                configure(result);
                break;
            case METHOD_IS_CONFIGURED:
                isConfigured(result);
                break;
            case METHOD_ADD_ADDRESS_BOOK:
                addAddressBook(methodCall, result);
                break;
            case METHOD_CREATE_CONTACT:
                createContact(methodCall, result);
                break;
            case METHOD_DELETE_CONTACT:
                deleteContact(methodCall, result);
                break;
            case METHOD_BLOCK_CONTACT:
                blockContact(methodCall, result);
                break;
            case METHOD_CREATE_CHAT_BY_CONTACT_ID:
                createChatByContactId(methodCall, result);
                break;
            case METHOD_CREATE_CHAT_BY_MESSAGE_ID:
                createChatByMessageId(methodCall, result);
                break;
            case METHOD_CREATE_GROUP_CHAT:
                createGroupChat(methodCall, result);
                break;
            case METHOD_GET_CONTACT:
                getContact(methodCall, result);
                break;
            case METHOD_GET_CONTACTS:
                getContacts(methodCall, result);
                break;
            case METHOD_GET_CHAT:
                getChat(methodCall, result);
                break;
            case METHOD_GET_CHAT_MESSAGES:
                getChatMessages(methodCall, result);
                break;
            case METHOD_CREATE_CHAT_MESSAGE:
                createChatMessage(methodCall, result);
                break;
            case METHOD_ADD_CONTACT_TO_CHAT:
                addContactToChat(methodCall, result);
                break;
            default:
                result.notImplemented();
        }
    }

    private void setConfig(MethodCall methodCall, MethodChannel.Result result) {
        if (!hasArgumentKeys(methodCall, ARGUMENT_KEY_TYPE, ARGUMENT_KEY_KEY, ARGUMENT_KEY_VALUE)) {
            resultErrorArgumentMissing(result);
            return;
        }
        String type = methodCall.argument(ARGUMENT_KEY_TYPE);
        if (type == null) {
            resultErrorArgumentMissingValue(result);
            return;
        }
        String key = methodCall.argument(ARGUMENT_KEY_KEY);
        switch (type) {
            case TYPE_INT: {
                Integer value = methodCall.argument(ARGUMENT_KEY_VALUE);
                if (value == null) {
                    resultErrorArgumentMissingValue(result);
                    return;
                }
                dcContext.setConfigInt(key, value);
                break;
            }
            case TYPE_STRING: {
                String value = methodCall.argument(ARGUMENT_KEY_VALUE);
                dcContext.setConfig(key, value);
                break;
            }
            default:
                resultErrorArgumentTypeMismatch(result, ARGUMENT_KEY_TYPE);
                break;
        }
        result.success(null);
    }


    private void getConfig(MethodCall methodCall, MethodChannel.Result result, String type) {
        if (!hasArgumentKeys(methodCall, ARGUMENT_KEY_KEY)) {
            resultErrorArgumentMissing(result);
            return;
        }
        String key = methodCall.argument(ARGUMENT_KEY_KEY);
        switch (type) {
            case TYPE_INT: {
                int resultValue = dcContext.getConfigInt(key);
                result.success(resultValue);
                break;
            }
            case TYPE_STRING: {
                String resultValue = dcContext.getConfig(key);
                result.success(resultValue);
                break;
            }
            default:
                resultErrorArgumentTypeMismatch(result, ARGUMENT_KEY_TYPE);
                break;
        }
    }

    private void configure(MethodChannel.Result result) {
        dcContext.configure();
        result.success(null);
    }

    private void isConfigured(MethodChannel.Result result) {
        boolean configured = dcContext.isConfigured() == 1;
        result.success(configured);
    }

    private void addAddressBook(MethodCall methodCall, MethodChannel.Result result) {
        if (!hasArgumentKeys(methodCall, ARGUMENT_ADDRESS_BOOK)) {
            resultErrorArgumentMissing(result);
            return;
        }
        String addressBook = methodCall.argument(ARGUMENT_ADDRESS_BOOK);
        if (addressBook == null || addressBook.isEmpty()) {
            resultErrorArgumentMissingValue(result);
            return;
        }
        contactCache.clear();
        int changedCount = dcContext.addAddressBook(addressBook);
        result.success(changedCount);
    }

    private void createContact(MethodCall methodCall, MethodChannel.Result result) {
        if (!hasArgumentKeys(methodCall, ARGUMENT_ADDRESS)) {
            resultErrorArgumentMissing(result);
            return;
        }
        String name = methodCall.argument(ARGUMENT_NAME);
        String address = methodCall.argument(ARGUMENT_ADDRESS);
        int contactId = dcContext.createContact(name, address);
        contactCache.put(contactId, dcContext.getContact(contactId));
        result.success(contactId);
    }

    private void deleteContact(MethodCall methodCall, MethodChannel.Result result) {
        if (!hasArgumentKeys(methodCall, ARGUMENT_ID)) {
            resultErrorArgumentMissing(result);
            return;
        }
        Integer contactId = methodCall.argument(ARGUMENT_ID);
        if (contactId == null) {
            resultErrorArgumentMissingValue(result);
            return;
        }
        boolean deleted = dcContext.deleteContact(contactId);
        if (deleted) {
            contactCache.delete(contactId);
        }
        result.success(deleted);
    }

    private void blockContact(MethodCall methodCall, MethodChannel.Result result) {
        if (!hasArgumentKeys(methodCall, ARGUMENT_ID)) {
            resultErrorArgumentMissing(result);
            return;
        }
        Integer contactId = methodCall.argument(ARGUMENT_ID);
        if (contactId == null) {
            resultErrorArgumentMissingValue(result);
            return;
        }
        dcContext.blockContact(contactId, 1);
        contactCache.delete(contactId);
        result.success(null);
    }


    private void createChatByContactId(MethodCall methodCall, MethodChannel.Result result) {
        if (!hasArgumentKeys(methodCall, ARGUMENT_ID)) {
            resultErrorArgumentMissing(result);
            return;
        }
        Integer contactId = methodCall.argument(ARGUMENT_ID);
        if (contactId == null) {
            resultErrorArgumentMissingValue(result);
            return;
        }
        int chatId = dcContext.createChatByContactId(contactId);
        chatCache.put(chatId, dcContext.getChat(chatId));
        result.success(chatId);
    }

    private void createChatByMessageId(MethodCall methodCall, MethodChannel.Result result) {
        if (!hasArgumentKeys(methodCall, ARGUMENT_ID)) {
            resultErrorArgumentMissing(result);
            return;
        }
        Integer messageId = methodCall.argument(ARGUMENT_ID);
        if (messageId == null) {
            resultErrorArgumentMissingValue(result);
            return;
        }
        int chatId = dcContext.createChatByMsgId(messageId);
        chatCache.put(chatId, dcContext.getChat(chatId));
        result.success(chatId);
    }

    private void createGroupChat(MethodCall methodCall, MethodChannel.Result result) {
        if (!hasArgumentKeys(methodCall, ARGUMENT_VERIFIED, ARGUMENT_NAME)) {
            resultErrorArgumentMissing(result);
            return;
        }
        Boolean verified = methodCall.argument(ARGUMENT_VERIFIED);
        if (verified == null) {
            resultErrorArgumentMissingValue(result);
            return;
        }
        String name = methodCall.argument(ARGUMENT_NAME);
        int chatId = dcContext.createGroupChat(verified, name);
        chatCache.put(chatId, dcContext.getChat(chatId));
        result.success(chatId);
    }


    private void getContact(MethodCall methodCall, MethodChannel.Result result) {
        if (!hasArgumentKeys(methodCall, ARGUMENT_ID)) {
            resultErrorArgumentMissing(result);
            return;
        }
        Integer id = methodCall.argument(ARGUMENT_ID);
        if (id == null) {
            resultErrorArgumentMissingValue(result);
            return;
        }
        DcContact contact = loadAndCacheContact(id);
        result.success(contact.getId());
    }

    DcContact loadAndCacheContact(Integer id) {
        DcContact contact = contactCache.get(id);
        if (contact == null) {
            contact = dcContext.getContact(id);
            contactCache.put(contact.getId(), contact);
        }
        return contact;
    }

    private void getContacts(MethodCall methodCall, MethodChannel.Result result) {
        if (!hasArgumentKeys(methodCall, ARGUMENT_FLAGS, ARGUMENT_QUERY)) {
            resultErrorArgumentMissing(result);
            return;
        }
        Integer flags = methodCall.argument(ARGUMENT_FLAGS);
        String query = methodCall.argument(ARGUMENT_QUERY);
        if (flags == null) {
            resultErrorArgumentMissingValue(result);
            return;
        }
        int[] contactIds = dcContext.getContacts(flags, query);
        for (int contactId : contactIds) {
            loadAndCacheContact(contactId);
        }
        result.success(contactIds);
    }

    private void getChat(MethodCall methodCall, MethodChannel.Result result) {
        if (!hasArgumentKeys(methodCall, ARGUMENT_ID)) {
            resultErrorArgumentMissing(result);
            return;
        }
        Integer id = methodCall.argument(ARGUMENT_ID);
        if (id == null) {
            resultErrorArgumentMissingValue(result);
            return;
        }
        DcChat chat = loadAndCacheChat(id);
        result.success(chat.getId());
    }

    DcChat loadAndCacheChat(Integer id) {
        DcChat chat = chatCache.get(id);
        if (chat == null) {
            chat = dcContext.getChat(id);
            chatCache.put(chat.getId(), chat);
        }
        return chat;
    }

    private void getChatMessages(MethodCall methodCall, MethodChannel.Result result) {
        if (!hasArgumentKeys(methodCall, ARGUMENT_ID)) {
            resultErrorArgumentMissing(result);
            return;
        }
        Integer id = methodCall.argument(ARGUMENT_ID);
        if (id == null) {
            resultErrorArgumentMissingValue(result);
            return;
        }

        int[] messageIds = dcContext.getChatMsgs(id, 0, 0);
        for (int messageId : messageIds) {
            DcMsg message = messageCache.get(messageId);
            if (message == null) {
                messageCache.put(messageId, dcContext.getMsg(messageId));
            }
        }
        result.success(messageIds);
    }

    DcMsg loadAndCacheChatMessage(Integer id) {
        DcMsg message = messageCache.get(id);
        if (message == null) {
            message = dcContext.getMsg(id);
            messageCache.put(message.getId(), message);
        }
        return message;
    }

    private void createChatMessage(MethodCall methodCall, MethodChannel.Result result) {
        if (!hasArgumentKeys(methodCall, ARGUMENT_ID, ARGUMENT_KEY_VALUE)) {
            resultErrorArgumentMissing(result);
            return;
        }
        Integer id = methodCall.argument(ARGUMENT_ID);
        if (id == null) {
            resultErrorArgumentMissingValue(result);
            return;
        }

        String text = methodCall.argument(ARGUMENT_KEY_VALUE);

        DcMsg newMsg = new DcMsg(dcContext, DcMsg.DC_MSG_TEXT);
        newMsg.setText(text);
        int messageId = dcContext.sendMsg(id, newMsg);
        result.success(messageId);
    }

    private void addContactToChat(MethodCall methodCall, MethodChannel.Result result) {
        if (!hasArgumentKeys(methodCall, ARGUMENT_CHAT_ID, ARGUMENT_CONTACT_ID)) {
            resultErrorArgumentMissing(result);
            return;
        }
        Integer chatId = methodCall.argument(ARGUMENT_CHAT_ID);
        Integer contactId = methodCall.argument(ARGUMENT_CONTACT_ID);
        if (chatId == null || contactId == null) {
            resultErrorArgumentMissingValue(result);
            return;
        }
        int successfullyAdded = dcContext.addContactToChat(chatId, contactId);
        result.success(successfullyAdded);
    }
}