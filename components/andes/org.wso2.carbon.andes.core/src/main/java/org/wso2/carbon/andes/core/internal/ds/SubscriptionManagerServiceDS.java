/*
 * Copyright (c) 2005-2014, WSO2 Inc. (http://www.wso2.org) All Rights Reserved.
 *
 *   WSO2 Inc. licenses this file to you under the Apache License,
 *   Version 2.0 (the "License"); you may not use this file except
 *   in compliance with the License.
 *   You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 *   Unless required by applicable law or agreed to in writing,
 *   software distributed under the License is distributed on an
 *   "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 *   KIND, either express or implied.  See the License for the
 *   specific language governing permissions and limitations
 *   under the License.
 */
package org.wso2.carbon.andes.core.internal.ds;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.osgi.service.component.ComponentContext;
import org.wso2.carbon.andes.core.SubscriptionManagerService;
import org.wso2.carbon.andes.core.internal.builder.SubscriptionManagerServiceBuilder;
import org.wso2.carbon.registry.core.service.RegistryService;
import org.wso2.carbon.user.core.service.RealmService;
import org.wso2.carbon.utils.ConfigurationContextService;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Deactivate;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.component.annotations.ReferenceCardinality;
import org.osgi.service.component.annotations.ReferencePolicy;

@Component(
        name = "SubscriptionManagerService.component",
        immediate = true)
public class SubscriptionManagerServiceDS {

    public static Log log = LogFactory.getLog(SubscriptionManagerServiceDS.class);

    @Activate
    protected void activate(ComponentContext context) {

        try {
            SubscriptionManagerService subscriptionManagerService = SubscriptionManagerServiceBuilder
                    .createSubscriptionManagerService();
            context.getBundleContext().registerService(SubscriptionManagerService.class.getName(),
                    subscriptionManagerService, null);
            log.info("Successfully created the subscription manager service");
        } catch (RuntimeException e) {
            log.error("Can not create subscription manager service ", e);
        }
    }

    @Reference(
            name = "realm.service",
            service = org.wso2.carbon.user.core.service.RealmService.class,
            cardinality = ReferenceCardinality.MANDATORY,
            policy = ReferencePolicy.DYNAMIC,
            unbind = "unsetRealmService")
    protected void setRealmService(RealmService realmService) {

        SubscriptionManagerServiceValueHolder.getInstance().registerRealmService(realmService);
    }

    protected void unsetRealmService(RealmService realmService) {

        SubscriptionManagerServiceValueHolder.getInstance().registerRealmService(null);
    }

    @Reference(
            name = "registry.service",
            service = org.wso2.carbon.registry.core.service.RegistryService.class,
            cardinality = ReferenceCardinality.MANDATORY,
            policy = ReferencePolicy.DYNAMIC,
            unbind = "unsetRegistryService")
    protected void setRegistryService(RegistryService registryService) {

        SubscriptionManagerServiceValueHolder.getInstance().registerRegistryService(registryService);
    }

    protected void unsetRegistryService(RegistryService registryService) {

    }

    @Reference(
            name = "configurationcontext.service",
            service = org.wso2.carbon.utils.ConfigurationContextService.class,
            cardinality = ReferenceCardinality.MANDATORY,
            policy = ReferencePolicy.DYNAMIC,
            unbind = "unsetConfigurationContextService")
    protected void setConfigurationContextService(ConfigurationContextService configurationContextService) {

        SubscriptionManagerServiceValueHolder.getInstance().registerConfigurationContextService
                (configurationContextService);
    }

    protected void unsetConfigurationContextService(ConfigurationContextService configurationContextService) {

    }
}
