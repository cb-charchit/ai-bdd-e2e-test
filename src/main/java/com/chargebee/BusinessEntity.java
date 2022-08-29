/**
 * @author Rajesh_Kumar on 19/09/2022
 *
 * To store Business entity related data
 */
package com.chargebee;

import org.apache.commons.collections4.BidiMap;

public class BusinessEntity {

    private final BidiMap<String, String> entityDetails;

    /**
     * BusinessEntity Constructor
     * @param entityDetails - Entity details Map
     */
    public BusinessEntity(BidiMap<String, String> entityDetails) {
        this.entityDetails = entityDetails;
    }

    /**
     * To get entity Id using entity name
     * @param entityName - Name of an entity
     * @return entityId
     */
    public String getEntityId(String entityName) {
        return entityDetails.get(entityName);
    }

    /**
     * To get entity name using entity Id
     * @param entityId - Entity Id
     * @return entityName
     */
    public String getEntityName(String entityId) {
        return entityDetails.getKey(entityId);
    }
}