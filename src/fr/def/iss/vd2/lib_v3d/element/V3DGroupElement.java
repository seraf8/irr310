// Copyright 2010 DEF
//
// This file is part of V3dScene.
//
// V3dScene is free software: you can redistribute it and/or modify
// it under the terms of the GNU Lesser General Public License as published by
// the Free Software Foundation, either version 3 of the License, or
// (at your option) any later version.
//
// V3dScene is distributed in the hope that it will be useful,
// but WITHOUT ANY WARRANTY; without even the implied warranty of
// MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
// GNU Lesser General Public License for more details.
//
// You should have received a copy of the GNU Lesser General Public License
// along with V3dScene.  If not, see <http://www.gnu.org/licenses/>.

package fr.def.iss.vd2.lib_v3d.element;

import java.util.ArrayList;
import java.util.List;

import fr.def.iss.vd2.lib_v3d.V3DContext;

/**
 *
 * @author fberto
 */
public class V3DGroupElement extends V3DCommonGroupElement {

    List<V3DElement> childrenList = new ArrayList<V3DElement>();

    public V3DGroupElement(V3DContext context) {
        super(context);
    }

    public void add(V3DElement element) {
        assert(element != null);
        childrenList.add(element);
    }

    @Override
    protected List<V3DElement> doGetChildren() {
        return childrenList;
    }

}
