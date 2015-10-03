/*
 * Copyright (C) 2015 Stefan Niederhauser (nidin@gmx.ch)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *         http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package guru.nidi.graphviz;

import guru.nidi.graphviz.attribute.Attributes;

import java.util.*;

/**
 *
 */
public class Graph implements Linkable, LinkTarget {
    final boolean strict;
    final boolean directed;
    final Name name;
    final Map<String, Object> attributes = new HashMap<>();
    final Set<Node> nodes = new LinkedHashSet<>();
    final Set<Graph> subgraphs = new LinkedHashSet<>();
    final List<Link> links = new ArrayList<>();

    private Graph(boolean strict, boolean directed, Name name) {
        this.strict = strict;
        this.directed = directed;
        this.name = name;
    }

    public static Graph named(Name name) {
        return new Graph(false, false, name);
    }

    public static Graph named(String name) {
        return named(Name.of(name));
    }

    public static Graph nameless() {
        return named("");
    }

    public Graph strict() {
        return new Graph(true, directed, name);
    }

    public Graph directed() {
        return new Graph(strict, true, name);
    }

    public Graph attr(String name, Object value) {
        attributes.put(name, value);
        return this;
    }

    public Graph attrs(Map<String, Object> attrs) {
        attributes.putAll(attrs);
        return this;
    }

    public Graph attrs(Object... keysAndValues) {
        return attrs(Attributes.from(keysAndValues));
    }

    public Graph with(Node... nodes) {
        for (final Node node : nodes) {
            with(node);
        }
        return this;
    }

    public Graph with(Node node) {
        nodes.add(node);
        return this;
    }

    public Graph with(Graph... subgraphs) {
        for (final Graph subgraph : subgraphs) {
            with(subgraph);
        }
        return this;
    }

    public Graph with(Graph subgraph) {
        subgraphs.add(subgraph);
        return this;
    }

    public Graph links(Link... links) {
        for (final Link link : links) {
            link(link);
        }
        return this;
    }

    public Graph link(Link link) {
        links.add(Link.between(this, link.to).attrs(link.attributes));
        return this;
    }

    @Override
    public Name name() {
        return name;
    }

    @Override
    public Collection<Link> links() {
        return links;
    }

}
