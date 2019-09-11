/**
 * Copyright 2019 Joern Muehlencord
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
package de.muehlencord.confluence.rest.core.api.domain.content;

import com.google.gson.annotations.Expose;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Joern Muehlencord (joern@muehlencord.de)
 */
public class AttachmentQueryResultsBean {

    @Expose
    private List<AttachmentQueryBean> results = new ArrayList<>();

    @Expose
    private Integer start;

    @Expose
    private Integer limit;

    @Expose
    private Integer size;

    public Integer getLimit() {
        return limit;
    }

    public void setLimit(Integer limit) {
        this.limit = limit;
    }

    public List<AttachmentQueryBean> getResults() {
        return results;
    }

    public void setResults(List<AttachmentQueryBean> results) {
        this.results = results;
    }

    public Integer getSize() {
        return size;
    }

    public void setSize(Integer size) {
        this.size = size;
    }

    public Integer getStart() {
        return start;
    }

    public void setStart(Integer start) {
        this.start = start;
    }

}
