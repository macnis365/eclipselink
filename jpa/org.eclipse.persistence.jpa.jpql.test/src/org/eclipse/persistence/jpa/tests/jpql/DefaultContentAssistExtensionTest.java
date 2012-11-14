/*******************************************************************************
 * Copyright (c) 2012 Oracle and/or its affiliates. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0 and Eclipse Distribution License v. 1.0
 * which accompanies this distribution.
 * The Eclipse Public License is available at http://www.eclipse.org/legal/epl-v10.html
 * and the Eclipse Distribution License is available at
 * http://www.eclipse.org/org/documents/edl-v10.php.
 *
 * Contributors:
 *     Oracle - initial API and implementation
 *
 ******************************************************************************/
package org.eclipse.persistence.jpa.tests.jpql;

import java.util.Collections;
import org.eclipse.persistence.jpa.jpql.ContentAssistExtension;
import org.eclipse.persistence.jpa.jpql.ContentAssistProposals.ClassType;

/**
 * Unit-test for {@link ContentAssistExtension} when the JPQL grammar is based on the JPA spec.
 *
 * @version 2.5
 * @since 2.5
 * @author Pascal Filion
 */
public final class DefaultContentAssistExtensionTest extends AbstractContentAssistExtensionTest {

	/**
	 * {@inheritDoc}
	 */
	@Override
	protected ContentAssistExtension buildContentAssistExtension() {
		return new ContentAssistExtension() {
			public Iterable<String> classNames(String prefix, ClassType type) {
				if (type == ClassType.INSTANTIABLE) {
					return filter(DefaultContentAssistExtensionTest.this.classNames(), prefix);
				}
				return filter(DefaultContentAssistExtensionTest.this.enumTypes(), prefix);
			}
			public Iterable<String> columnNames(String tableName, String prefix) {
				return Collections.emptyList();
			}
			public Iterable<String> tableNames(String prefix) {
				return Collections.emptyList();
			}
		};
	}
}