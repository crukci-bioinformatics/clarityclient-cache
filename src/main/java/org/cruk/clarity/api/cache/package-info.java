/*
 * CRUK-CI Clarity REST API Java Client.
 * Copyright (C) 2013 Cancer Research UK Cambridge Institute.
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

/**
 * Package supporting caching of Clarity entities in memory
 * (or, if so configured, on disk) to reduce the amount of traffic being
 * sent to and from the REST API.
 *
 * <p>
 * At present, the "stateful" objects (specifically, the {@code Artifact} class)
 * produces some problems. See the documentation for {@link org.cruk.clarity.api.cache.ClarityAPICache}
 * for further information.
 * </p>
 *
 * <p>
 * The cache is implemented using EhCache. The default version of
 * this file is packaged with the JAR, so if you need to change it create a replacement
 * that is before this project's JAR file on the class path.
 * </p>
 *
 * <p>
 * Caches are configured in the {@link org.cruk.clarity.api.cache.spring.ClarityClientCacheConfiguration}
 * Spring configuration class. If these need to be changed, create a subclass for your own configuration
 * and override the
 * {@link org.cruk.clarity.api.cache.spring.ClarityClientCacheConfiguration#clarityCacheManager() clarityCacheManager()}
 * method, creating your caches as necessary.
 * </p>
 *
 * @see <a href="https://ehcache.org/">https://ehcache.org/</a>
 */
package org.cruk.clarity.api.cache;
