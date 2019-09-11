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
 * Copyright (C) 2016-2019 OX Software GmbH
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

import Foundation

class DcLot {
    private var dcLotPointer: OpaquePointer?
    
    // takes ownership of specified pointer
    init(_ dcLotPointer: OpaquePointer) {
        self.dcLotPointer = dcLotPointer
    }
    
    deinit {
        dc_lot_unref(dcLotPointer)
    }
    
    var text1: String? {
        guard let cString = dc_lot_get_text1(dcLotPointer) else { return nil }
        let swiftString = String(cString: cString)
        free(cString)
        return swiftString
    }
    
    var text1Meaning: Int {
        return Int(dc_lot_get_text1_meaning(dcLotPointer))
    }
    
    var text2: String? {
        guard let cString = dc_lot_get_text2(dcLotPointer) else { return nil }
        let swiftString = String(cString: cString)
        free(cString)
        return swiftString
    }
    
    var timestamp: Int64 {
        return Int64(dc_lot_get_timestamp(dcLotPointer))
    }
    
    var state: Int {
        return Int(dc_lot_get_state(dcLotPointer))
    }
    
    var id: Int {
        return Int(dc_lot_get_id(dcLotPointer))
    }
}

enum ChatType: Int {
    case SINGLE = 100
    case GROUP = 120
    case VERYFIEDGROUP = 130
}

enum MessageViewType: CustomStringConvertible {
    case audio
    case file
    case gif
    case image
    case text
    case video
    case voice
    
    var description: String {
        switch self {
        // Use Internationalization, as appropriate.
        case .audio: return "Audio"
        case .file: return "File"
        case .gif: return "GIF"
        case .image: return "Image"
        case .text: return "Text"
        case .video: return "Video"
        case .voice: return "Voice"
        }
    }
}

func strToBool(_ value: String?) -> Bool {
    if let vStr = value {
        if let vInt = Int(vStr) {
            return vInt == 1
        }
        return false
    }
    
    return false
}
