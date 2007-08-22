/*
 * Copyright 2007 The Kuali Foundation
 *
 * Licensed under the Educational Community License, Version 1.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.opensource.org/licenses/ecl1.php
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package edu.iu.uis.eden.messaging;

import java.util.ArrayList;
import java.util.List;

import org.kuali.bus.services.KSBServiceLocator;
import org.kuali.rice.core.Core;
import org.kuali.rice.exceptions.RiceRuntimeException;
import org.kuali.rice.util.DataAccessUtils;

import edu.iu.uis.eden.messaging.dao.ServiceInfoDAO;

public class ServiceRegistryImpl implements ServiceRegistry {

	private ServiceInfoDAO dao;

	public void saveEntry(ServiceInfo entry) {
		try {
			Object service = entry.getServiceDefinition().getService();
			entry.getServiceDefinition().setService(null);
			entry.setSerializedMessageEntity(KSBServiceLocator.getMessageHelper().serializeObject(entry.getServiceDefinition()));
			entry.getServiceDefinition().setService(service);
		} catch (Exception e) {
			throw new RiceRuntimeException(e);
		}
		getDao().addEntry(entry);
	}

	public List<ServiceInfo> fetchAll() {
		return getDao().fetchAll();
	}

	public List<ServiceInfo> fetchAllActive() {
	    return dao.fetchAllActive();
	}

	public List<ServiceInfo> findLocallyPublishedServices(String ipNumber, String messageEntity) {
		if (Core.getCurrentContextConfig().getDevMode()) {
			return new ArrayList<ServiceInfo>();
		}
		return getDao().findLocallyPublishedServices(ipNumber, messageEntity);
	}

	public void removeEntry(ServiceInfo entry) {
		getDao().removeEntry(entry);
	}

	public void removeLocallyPublishedServices(String ipNumber, String messageEntity) {
		getDao().removeLocallyPublishedServices(ipNumber, messageEntity);
	}

	public ServiceInfoDAO getDao() {
		return this.dao;
	}

	public void setDao(ServiceInfoDAO dao) {
		this.dao = dao;
	}

	public void remove(List<ServiceInfo> serviceEntries) {
		for (ServiceInfo info : serviceEntries) {
			removeEntry(info);
		}
	}

	public void save(List<ServiceInfo> serviceEntries) {
		for (ServiceInfo info : serviceEntries) {
			saveEntry(info);
		}
	}

	public void markServicesDead(List<ServiceInfo> serviceEntries) {
		for (ServiceInfo info : serviceEntries) {
			// there is contention on these records from multiple nodes and odds
			// are the
			// one we have in memory is stale. refetch and mork dead.
			ServiceInfo currentInfo = getDao().findServiceInfo(info.getMessageEntryId());
			currentInfo.setAlive(false);
			try {
				saveEntry(currentInfo);
			} catch (Exception e) {
				boolean isOptimisticLockExp = DataAccessUtils.isOptimisticLockFailure(e);
				// suppress optimistic lock exceptions, it's collision with
				// other nodes
				if (!isOptimisticLockExp) {
					throw (RuntimeException) e;
				}
			}

		}
	}
}