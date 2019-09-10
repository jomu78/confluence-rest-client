/**
 * Copyright 2016 Micromata GmbH
 * Modifications Copyright 2017 Luca Tagliani
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
package com.github.jomu78.confluence.rest.core.api.domain.cql;

import com.google.gson.annotations.Expose;
import com.github.jomu78.confluence.rest.core.api.domain.content.ContentBean;
import com.github.jomu78.confluence.rest.core.api.domain.space.SpaceBean;

/**
 * @author Christian Schulze (c.schulze@micromata.de)
 */
public class SearchResultBean {

    @Expose
    private SpaceBean space;

    @Expose
    private ContentBean content;

    public ContentBean getContent() {
        return content;
    }

    public void setContent(ContentBean content) {
        this.content = content;
    }

    public SpaceBean getSpace() {
        return space;
    }

    public void setSpace(SpaceBean space) {
        this.space = space;
    }
}
