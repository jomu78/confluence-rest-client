/**
 * Copyright 2016 Micromata GmbH
 * Modifications Copyright 2017 Martin Böhmer
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.github.jomu78.confluence.rest.core.api.domain;

import com.google.gson.annotations.Expose;
import com.github.jomu78.confluence.rest.core.api.domain.common.LinksBean;

/**
 * @author Christian Schulze (c.schulze@micromata.de)
 * @author Martin Böhmer
 */
public abstract class BaseBean {

    /**
     * Indicates whether the bean was changed programmatically and requires to
     * be updated, i.e. send to Confluence
     */
    private boolean dirty = false;

    @Expose
    private String id;

    @Expose
    private String type;

    @Expose
    private LinksBean _links;

    public BaseBean() {
    }

    public BaseBean(String id) {
        this.id = id;
    }

    public BaseBean(String id, String type, LinksBean links) {
        this.id = id;
        this.type = type;
        this._links = links;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public LinksBean getLinks() {
        return _links;
    }

    public void setLinks(LinksBean links) {
        this._links = links;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setDirty() {
        dirty = true;
    }

    public boolean isDirty() {
        // Unsaved conent is always dirty
        if (getId() == null) {
            return true;
        }
        return dirty;
    }

}
