/**
 *    Copyright 2015, Big Switch Networks, Inc.
 *    Originally created by Pengfei Lu, Network and Cloud Computing Laboratory, Dalian University of Technology, China 
 *    Advisers: Keqiu Li, Heng Qi and Haisheng Yu 
 *    This work is supported by the State Key Program of National Natural Science of China(Grant No. 61432002) 
 *    and Prospective Research Project on Future Networks in Jiangsu Future Networks Innovation Institute.
 *    
 *    Licensed under the Apache License, Version 2.0 (the "License"); you may 
 *    not use this file except in compliance with the License. You may obtain
 *    a copy of the License at
 *    
 *         http://www.apache.org/licenses/LICENSE-2.0 
 *    
 *    Unless required by applicable law or agreed to in writing, software 
 *    distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 *    WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 *    License for the specific language governing permissions and limitations
 *    under the License.
 **/

package net.floodlightcontroller.accesscontrollist.ap;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import net.floodlightcontroller.accesscontrollist.util.IPAddressUtil;
import net.floodlightcontroller.packet.IPv4;

public class APManager {

	private Set<AP> apSet = new HashSet<AP>();

	public void addAP(AP ap) {
		this.apSet.add(ap);
	}

	/**
	 * get dpid set relating to the given CIDR IP
	 * 从控制器中 获取所有满足该 CIDR前缀码的 IP的集合 所对应的dpid的集合（通过AP类来对应）
	 * 如 192.168.0.0／16 ，
	 * cidrPrefix即192.168.0.0的32位二进制表示形式（用长度32位的int整形表示）， cidrMaskBits则是网络前缀位数 16位
	 */
	public Set<String> getDpidSet(int cidrPrefix, int cidrMaskBits) {
		Set<String> dpidSet = new HashSet<String>();

		Iterator<AP> iter = apSet.iterator();
		if (cidrMaskBits != 32) {
			while (iter.hasNext()) {
				AP ap = iter.next();
				if (IPAddressUtil.containIP(cidrPrefix, cidrMaskBits,
						IPv4.toIPv4Address(ap.getIp()))) {
					dpidSet.add(ap.getDpid());
				}
			}
		} else {
			while (iter.hasNext()) {
				AP ap = iter.next();
				if (IPAddressUtil.containIP(cidrPrefix, cidrMaskBits,
						IPv4.toIPv4Address(ap.getIp()))) {
					dpidSet.add(ap.getDpid());
					return dpidSet;
				}
			}
		}
		return dpidSet;
	}

}
