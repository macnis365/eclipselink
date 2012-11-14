/*******************************************************************************
 * Copyright (c) 2006, 2012 Oracle and/or its affiliates. All rights reserved.
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
package org.eclipse.persistence.jpa.jpql;

import org.eclipse.persistence.jpa.jpql.parser.AbsExpression;
import org.eclipse.persistence.jpa.jpql.parser.ArithmeticFactor;
import org.eclipse.persistence.jpa.jpql.parser.AvgFunction;
import org.eclipse.persistence.jpa.jpql.parser.ConcatExpression;
import org.eclipse.persistence.jpa.jpql.parser.LengthExpression;
import org.eclipse.persistence.jpa.jpql.parser.LocateExpression;
import org.eclipse.persistence.jpa.jpql.parser.LowerExpression;
import org.eclipse.persistence.jpa.jpql.parser.ModExpression;
import org.eclipse.persistence.jpa.jpql.parser.SqrtExpression;
import org.eclipse.persistence.jpa.jpql.parser.SubstringExpression;
import org.eclipse.persistence.jpa.jpql.parser.SumFunction;
import org.eclipse.persistence.jpa.jpql.parser.TrimExpression;
import org.eclipse.persistence.jpa.jpql.parser.UpperExpression;

/**
 * This visitor traverses the JPQL parsed tree and gathers the possible proposals at a given position.
 * <p>
 * Example:
 * <pre><code> // Have the external form of an IQuery
 * {@link org.eclipse.persistence.jpa.jpql.spi.IQuery IQuery} query = ...
 *
 * // Create a JPQLQueryContext
 * {@link JPQLQueryContext} context = new JPQLQueryContext();
 * context.{@link JPQLQueryContext#setQuery(org.eclipse.persistence.jpa.jpql.spi.IQuery) setQuery(query)};
 *
 * // Create a map of the positions within the parsed tree
 * {@link org.eclipse.persistence.jpa.jpql.parser.QueryPosition QueryPosition} queryPosition = context.getJPQLExpression().buildPosition(query.getExpression(), position);
 *
 * // Create the visitor and visit the parsed tree
 * ContentAssistVisitor visitor = new ContentAssistVisitor(context);
 * visitor.{@link #prepare(org.eclipse.persistence.jpa.jpql.parser.QueryPosition) prepare(queryPosition)};
 * queryPosition.getExpression().accept(visitor);
 *
 * // Retrieve the proposals
 * {@link org.eclipse.persistence.jpa.jpql.ContentAssistProposals ContentAssistProposals} proposals = visitor.getProposals();
 *
 * // Only required if the visitor is cached
 * visitor.dispose();
 *
 * // Only required if the context is cached
 * context.dispose();
 * </code></pre>
 * <p>
 * Provisional API: This interface is part of an interim API that is still under development and
 * expected to change significantly before reaching stability. It is available at this early stage
 * to solicit feedback from pioneering adopters on the understanding that any code that uses this
 * API will almost certainly be broken (repeatedly) as the API evolves.
 *
 * @version 2.5
 * @since 2.3
 * @author Pascal Filion
 */
public class DefaultContentAssistVisitor extends AbstractContentAssistVisitor {

	/**
	 * Creates a new <code>DefaultContentAssistVisitor</code>.
	 *
	 * @param queryContext The context used to query information about the query
	 * @exception NullPointerException The {@link JPQLQueryContext} cannot be <code>null</code>
	 */
	public DefaultContentAssistVisitor(JPQLQueryContext queryContext) {
		super(queryContext);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	protected AcceptableTypeVisitor buildAcceptableTypeVisitor() {
		return new AcceptableTypeVisitor();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	protected boolean isJoinFetchIdentifiable() {
		return false;
	}

	protected class AcceptableTypeVisitor extends AbstractContentAssistVisitor.AcceptableTypeVisitor {

		/**
		 * {@inheritDoc}
		 */
		@Override
		public void visit(AbsExpression expression) {
			type = getType(Number.class);
		}

		/**
		 * {@inheritDoc}
		 */
		@Override
		public void visit(ArithmeticFactor expression) {
			type = getType(Number.class);
		}

		/**
		 * {@inheritDoc}
		 */
		@Override
		public void visit(AvgFunction expression) {
			type = getType(Number.class);
		}

		/**
		 * {@inheritDoc}
		 */
		@Override
		public void visit(ConcatExpression expression) {
			type = getType(CharSequence.class);
		}

		/**
		 * {@inheritDoc}
		 */
		@Override
		public void visit(LengthExpression expression) {
			type = getType(CharSequence.class);
		}

		/**
		 * {@inheritDoc}
		 */
		@Override
		public void visit(LocateExpression expression) {
			// TODO: Handle the position
			type = getType(CharSequence.class);
		}

		/**
		 * {@inheritDoc}
		 */
		@Override
		public void visit(LowerExpression expression) {
			type = getType(CharSequence.class);
		}

		/**
		 * {@inheritDoc}
		 */
		@Override
		public void visit(ModExpression expression) {
			// In theory we would only allow Long and Integer
			type = getType(Number.class);
		}

		/**
		 * {@inheritDoc}
		 */
		@Override
		public void visit(SqrtExpression expression) {
			type = getType(Number.class);
		}

		/**
		 * {@inheritDoc}
		 */
		@Override
		public void visit(SubstringExpression expression) {
			// TODO: Handle the position
			type = getType(CharSequence.class);
		}

		/**
		 * {@inheritDoc}
		 */
		@Override
		public void visit(SumFunction expression) {
			type = getType(Number.class);
		}

		/**
		 * {@inheritDoc}
		 */
		@Override
		public void visit(TrimExpression expression) {
			type = getType(CharSequence.class);
		}

		/**
		 * {@inheritDoc}
		 */
		@Override
		public void visit(UpperExpression expression) {
			type = getType(CharSequence.class);
		}
	}
}