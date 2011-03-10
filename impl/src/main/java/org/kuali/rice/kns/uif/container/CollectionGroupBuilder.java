/*
 * Copyright 2011 The Kuali Foundation
 *
 * Licensed under the Educational Community License, Version 1.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.opensource.org/licenses/ecl1.php
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.kuali.rice.kns.uif.container;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.kuali.rice.kns.uif.BindingInfo;
import org.kuali.rice.kns.uif.DataBinding;
import org.kuali.rice.kns.uif.UifParameters;
import org.kuali.rice.kns.uif.UifPropertyPaths;
import org.kuali.rice.kns.uif.field.ActionField;
import org.kuali.rice.kns.uif.field.Field;
import org.kuali.rice.kns.uif.layout.CollectionLayoutManager;
import org.kuali.rice.kns.uif.util.ComponentUtils;
import org.kuali.rice.kns.uif.util.ModelUtils;
import org.kuali.rice.kns.web.spring.form.UifFormBase;

/**
 * Builds out the <code>Field</code> instances for a collection group with a
 * series of steps that interact with the configured
 * <code>CollectionLayoutManager</code> to assemble the fields as necessary for
 * the layout
 * 
 * @author Kuali Rice Team (rice.collab@kuali.org)
 */
public class CollectionGroupBuilder implements Serializable {
	private static final long serialVersionUID = -4762031957079895244L;

	/**
	 * Creates the <code>Field</code> instances that make up the table
	 * 
	 * <p>
	 * The corresponding collection is retrieved from the model and iterated
	 * over to create the necessary fields. The binding path for fields that
	 * implement <code>DataBinding</code> is adjusted to point to the collection
	 * line it is apart of. For example, field 'number' of collection 'accounts'
	 * for line 1 will be set to 'accounts[0].number', and for line 2
	 * 'accounts[1].number'. Finally parameters are set on the line's action
	 * fields to indicate what collection and line they apply to.
	 * </p>
	 * 
	 * @param view
	 *            - View instance the collection belongs to
	 * @param model
	 *            - Top level object containing the data
	 * @param collectionGroup
	 *            - CollectionGroup component for the collection
	 */
	public void build(View view, Object model, CollectionGroup collectionGroup) {
		// create add line
		if (collectionGroup.isRenderAddLine() && !collectionGroup.isReadOnly()) {
			buildAddLine(view, model, collectionGroup);
		}

		// get the collection for this group from the model
		List<Object> modelCollection = ModelUtils.getPropertyValue(model, ((DataBinding) collectionGroup)
				.getBindingInfo().getBindingPath());

		// for each collection row create a new Group
		if (modelCollection != null) {
			for (int index = 0; index < modelCollection.size(); index++) {
				String bindingPathPrefix = collectionGroup.getBindingInfo().getBindingName() + "[" + index + "]";
				if (StringUtils.isNotBlank(collectionGroup.getBindingInfo().getBindByNamePrefix())) {
					bindingPathPrefix = collectionGroup.getBindingInfo().getBindByNamePrefix() + "."
							+ bindingPathPrefix;
				}

				List<ActionField> actions = getLineActions(view, model, collectionGroup, index);
				buildLine(view, model, collectionGroup, bindingPathPrefix, actions, false, index);
			}
		}
	}

	/**
	 * Builds the fields for holding the collection add line and if necessary
	 * makes call to setup the new line instance
	 * 
	 * @param view
	 *            - view instance the collection belongs to
	 * @param collectionGroup
	 *            - collection group the layout manager applies to
	 * @param model
	 *            - Object containing the view data, should extend UifFormBase
	 *            if using framework managed new lines
	 */
	protected void buildAddLine(View view, Object model, CollectionGroup collectionGroup) {
		String addLineBindingPath = "";
		boolean bindAddLineToForm = false;

		// determine whether the add line binds to the generic form map or a
		// specified property
		if (StringUtils.isNotBlank(collectionGroup.getAddLineName())) {
			addLineBindingPath = collectionGroup.getAddLineBindingInfo().getBindingPath();
		}
		else {
			addLineBindingPath = initializeNewCollectionLine(view, model, collectionGroup, false);
			collectionGroup.getAddLineBindingInfo().setBindingPath(addLineBindingPath);
			bindAddLineToForm = true;
		}

		List<ActionField> actions = getAddLineActions(view, model, collectionGroup);
		buildLine(view, model, collectionGroup, addLineBindingPath, actions, bindAddLineToForm, -1);
	}

	/**
	 * Builds the field instances for the collection line. A copy of the
	 * configured items on the <code>CollectionGroup</code> is made and adjusted
	 * for the line (id and binding). Then a call is made to the
	 * <code>CollectionLayoutManager</code> to assemble the line as necessary
	 * for the layout
	 * 
	 * @param view
	 *            - view instance the collection belongs to
	 * @param model
	 *            - top level object containing the data
	 * @param collectionGroup
	 *            - collection group component for the collection
	 * @param bindingPath
	 *            - binding path for the line fields (if DataBinding)
	 * @param actions
	 *            - List of actions to set in the lines action column
	 * @param bindLineToForm
	 *            - whether the bindToForm property on the items bindingInfo
	 *            should be set to true (needed for add line)
	 * @param lineIndex
	 *            - index of the line in the collection, or -1 if we are
	 *            building the add line
	 */
	protected void buildLine(View view, Object model, CollectionGroup collectionGroup, String bindingPath,
			List<ActionField> actions, boolean bindToForm, int lineIndex) {
		CollectionLayoutManager layoutManager = (CollectionLayoutManager) collectionGroup.getLayoutManager();

		// copy group items for new line
		List<? extends Field> lineFields = ComponentUtils.copyFieldList(collectionGroup.getItems(), bindingPath);

		// update ids and binding
		String idSuffix = "_" + lineIndex;
		ComponentUtils.updateIdsWithSuffix(lineFields, idSuffix);
		if (bindToForm) {
			ComponentUtils.setComponentsPropertyDeep(lineFields, UifPropertyPaths.BIND_TO_FORM, new Boolean(true));
		}

		// add generated fields to the view's index
		view.getViewIndex().addFields(lineFields);

		// update ids on actions
		ComponentUtils.updateIdsWithSuffix(actions, idSuffix);

		// invoke layout manager to build the complete line
		layoutManager.buildLine(view, model, collectionGroup, lineFields, bindingPath, actions, idSuffix, lineIndex);
	}

	/**
	 * Creates new <code>ActionField</code> instances for the line
	 * 
	 * <p>
	 * Adds context to the action fields for the given line so that the line the
	 * action was performed on can be determined when that action is selected
	 * </p>
	 * 
	 * @param view
	 *            - view instance the collection belongs to
	 * @param model
	 *            - top level object containing the data
	 * @param collectionGroup
	 *            - collection group component for the collection
	 * @param lineIndex
	 *            - index of the line the actions should apply to
	 */
	protected List<ActionField> getLineActions(View view, Object model, CollectionGroup collectionGroup, int lineIndex) {
		List<ActionField> lineActions = ComponentUtils.copyFieldList(collectionGroup.getActionFields());
		for (ActionField actionField : lineActions) {
			actionField.addActionParameter(UifParameters.SELLECTED_COLLECTION_PATH, collectionGroup.getBindingInfo()
					.getBindingPath());
			actionField.addActionParameter(UifParameters.SELECTED_LINE_INDEX, Integer.toString(lineIndex));
		}

		return lineActions;
	}

	/**
	 * Creates new <code>ActionField</code> instances for the add line
	 * 
	 * <p>
	 * Adds context to the action fields for the add line so that the collection
	 * the action was performed on can be determined
	 * </p>
	 * 
	 * @param view
	 *            - view instance the collection belongs to
	 * @param model
	 *            - top level object containing the data
	 * @param collectionGroup
	 *            - collection group component for the collection
	 */
	protected List<ActionField> getAddLineActions(View view, Object model, CollectionGroup collectionGroup) {
		List<ActionField> lineActions = ComponentUtils.copyFieldList(collectionGroup.getAddLineActionFields());
		for (ActionField actionField : lineActions) {
			actionField.addActionParameter(UifParameters.SELLECTED_COLLECTION_PATH, collectionGroup.getBindingInfo()
					.getBindingPath());
		}

		return lineActions;
	}

	/**
	 * Initializes a new instance of the collection class (configured on the
	 * collection group) in the UifFormBase generic newCollectionLines Map
	 * 
	 * @see org.kuali.rice.kns.uif.container.CollectionGroup.
	 *      initializeNewCollectionLine(View, Object, CollectionGroup, boolean)
	 */
	public String initializeNewCollectionLine(View view, Object model, CollectionGroup collectionGroup,
			boolean clearExistingLine) {
		if (!(model instanceof UifFormBase)) {
			throw new RuntimeException("Cannot create new collection line for group: "
					+ collectionGroup.getPropertyName() + ". Model does not extend " + UifFormBase.class.getName());
		}

		// get new collection line map from form
		Map<String, Object> newCollectionLines = ModelUtils.getPropertyValue(model,
				UifPropertyPaths.NEW_COLLECTION_LINES);
		if (newCollectionLines == null) {
			newCollectionLines = new HashMap<String, Object>();
			ModelUtils.setPropertyValue(model, UifPropertyPaths.NEW_COLLECTION_LINES, newCollectionLines);
		}

		// if there is not an instance available or we need to clear create a
		// new instance
		BindingInfo bindingInfo = collectionGroup.getBindingInfo();
		if (!newCollectionLines.containsKey(bindingInfo.getBindingPath())
				|| (newCollectionLines.get(bindingInfo.getBindingPath()) == null) || clearExistingLine) {
			// create new instance of the collection type for the add line
			try {
				Object newLineInstance = collectionGroup.getCollectionObjectClass().newInstance();
				newCollectionLines.put(bindingInfo.getBindingPath(), newLineInstance);
			}
			catch (Exception e) {
				throw new RuntimeException("Cannot create new add line instance for group: "
						+ collectionGroup.getPropertyName() + " with collection class: "
						+ collectionGroup.getCollectionObjectClass().getName());
			}
		}

		return UifPropertyPaths.NEW_COLLECTION_LINES + "['" + bindingInfo.getBindingPath() + "']";
	}

}
