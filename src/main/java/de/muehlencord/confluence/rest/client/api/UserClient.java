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
package de.muehlencord.confluence.rest.client.api;

import de.muehlencord.confluence.rest.core.api.domain.UserBean;

import java.net.URISyntaxException;
import java.util.concurrent.Future;

/**
 * @author Christian Schulze (c.schulze@micromata.de)
 */
public interface UserClient {

    /**
     * Get the confluence user by username (example: admin)
     *
     * @param username the username
     *
     * @return Future with the UserBean
     *
     * @throws java.net.URISyntaxException (never thrown due to future)
     */
    Future<UserBean> getUserByUsername(String username) throws URISyntaxException;

    /**
     * Get the confluence user by key (example:
     * 402880824ff933a4014ff9345d7c0002)
     *
     * @param key the key
     *
     * @return Future with the UserBean
     *
     * @throws java.net.URISyntaxException (never thrown due to future)
     */
    Future<UserBean> getUserByKey(String key) throws URISyntaxException;

    /**
     * Get the current logged in user
     *
     * @return Future with the UserBean
     *
     * @throws java.net.URISyntaxException (never thrown due to future)
     */
    Future<UserBean> getCurrentUser() throws URISyntaxException;

    /**
     * Get the Anonymous User
     *
     * @return Future with the UserBean
     *
     * @throws java.net.URISyntaxException (never thrown due to future)
     */
    Future<UserBean> getAnonymousUser() throws URISyntaxException;
}
