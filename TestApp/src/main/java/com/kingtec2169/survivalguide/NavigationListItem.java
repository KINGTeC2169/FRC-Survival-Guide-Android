// Copyright 2014 FRC team 2169 KING TeC
//
// Licensed under the Apache License, Version 2.0 (the "License");
// you may not use this file except in compliance with the License.
// You may obtain a copy of the License at
//
//    http://www.apache.org/licenses/LICENSE-2.0
// 
// Unless required by applicable law or agreed to in writing, software
// distributed under the License is distributed on an "AS IS" BASIS,
// WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
// See the License for the specific language governing permissions and
// limitations under the License.

package com.kingtec2169.survivalguide;

// Created by Ryan Zoeller of FIRST FRC team 2169.
// This class acts as a holder for the list items in the ExpandableListNavigationFragment.

public class NavigationListItem {
    public String name;
    public String description;
    public String id;

    public NavigationListItem() {
        super();
    }

    public NavigationListItem(String id, String name, String description) {
        super();
        this.id = id;
        this.name = name;
        this.description = description;
    }
}
