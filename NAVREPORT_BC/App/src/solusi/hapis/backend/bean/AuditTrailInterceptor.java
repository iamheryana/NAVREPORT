package solusi.hapis.backend.bean;

import java.io.Serializable;
import java.util.Date;

import org.hibernate.EmptyInterceptor;
import org.hibernate.type.Type;
import org.springframework.security.core.context.SecurityContextHolder;

import solusi.hapis.policy.model.UserImpl;

public class AuditTrailInterceptor extends EmptyInterceptor {

	private static final long serialVersionUID = 5467834819708972515L;
	private String username = "N/A";

	@Override
	public boolean onFlushDirty(Object entity, Serializable id,
			Object[] currentState, Object[] previousState,
			String[] propertyNames, Type[] types) {
		UserImpl user = (UserImpl) SecurityContextHolder.getContext()
				.getAuthentication().getPrincipal();
		if (user != null)
			username = user.getUsername();
		setValue(currentState, propertyNames, "updatedBy", username);
		setValue(currentState, propertyNames, "updatedOn", new Date());
		return true;

	}

	@Override
	public boolean onSave(Object entity, Serializable id, Object[] state,
			String[] propertyNames, Type[] types) {

		if (SecurityContextHolder.getContext().getAuthentication() != null) {
			UserImpl user = (UserImpl) SecurityContextHolder.getContext()
					.getAuthentication().getPrincipal();
			if (user != null)
				username = user.getUsername();
		}

		setValue(state, propertyNames, "createdBy", username);
		setValue(state, propertyNames, "createdOn", new Date());
		setValue(state, propertyNames, "updatedBy", username);
		setValue(state, propertyNames, "updatedOn", new Date());
		return true;
	}

	private void setValue(Object[] currentState, String[] propertyNames,
			String propertyToSet, Object value) {

		L1: for (int i = 0; i < propertyNames.length; i++) {
			if (propertyNames[i].equals(propertyToSet)) {
				currentState[i] = value;
				break L1;
			}
		}

	}

}
