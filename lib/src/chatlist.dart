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

import 'dart:async';

import 'package:delta_chat_core/delta_chat_core.dart';
import 'package:delta_chat_core/src/base.dart';

class ChatList extends Base {
  static const String _identifier = "chatList";

  static const String methodChatListInternalSetup = "chatList_internal_setup";
  static const String methodChatListInternalTearDown = "chatList_internal_tearDown";
  static const String methodChatListGetCnt = "chatList_getCnt";
  static const String methodChatListGetId = "chatList_getId";
  static const String methodChatListGetChat = "chatList_getChat";
  static const String methodChatListGetMsgId = "chatList_getMsgId";
  static const String methodChatListGetMsg = "chatList_getMsg";
  static const String methodChatListGetSummary = "chatList_getSummary";
  static const String chatListNoSpecial = "chatList_noSpecial";
  static const String chatListAll = "chatList_all";

  static const int typeVerifiedOnly = 1;
  static const int typeAddSelf = 2;
  static const int typeArchivedOnly = 0x01;
  static const int typeNoSpecials = 0x02;
  static const int typeAddAllDoneHint = 0x04;

  final DeltaChatCore core = DeltaChatCore();

  int _id;

  @override
  int get id => _id;

  @override
  String get identifier => _identifier;

  Future<void> setupAsync([String query, int chatListType = typeNoSpecials]) async {
    _id = await core.invokeMethodAsync(methodChatListInternalSetup, getSetupArguments(chatListType, query));
  }

  Future<int> tearDownAsync() async {
    return await core.invokeMethodAsync(methodChatListInternalTearDown, getDefaultArguments());
  }

  Future<int> getChatCntAsync() async {
    return await core.invokeMethodAsync(methodChatListGetCnt, getDefaultArguments());
  }

  Future<int> getChatIdAsync(int index) async {
    return await core.invokeMethodAsync(methodChatListGetId, getIndexArguments(index));
  }

  Future<int> getChatMsgIdAsync(int index) async {
    return await core.invokeMethodAsync(methodChatListGetMsgId, getIndexArguments(index));
  }

  Future<int> getChatAsync(int index) async {
    return await core.invokeMethodAsync(methodChatListGetChat, getIndexArguments(index));
  }

  Future<dynamic> getChatMsgAsync(int index) async {
    return await core.invokeMethodAsync(methodChatListGetMsg, getIndexArguments(index));
  }

  Future<List<dynamic>> getChatSummaryAsync(int index) async {
    return await core.invokeMethodAsync(methodChatListGetSummary, getIndexArguments(index));
  }

  @override
  getDefaultArguments() => <String, dynamic>{Base.argumentCacheId: _id};

  Map<String, dynamic> getSetupArguments(int chatListType, [String query]) => <String, dynamic>{Base.argumentType: chatListType, Base.argumentQuery: query};

  Map<String, dynamic> getIndexArguments(int index) => <String, dynamic>{Base.argumentCacheId: _id, Base.argumentIndex: index};

}
