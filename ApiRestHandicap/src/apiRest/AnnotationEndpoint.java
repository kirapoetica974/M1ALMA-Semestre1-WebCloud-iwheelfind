package apiRest;

import apiRest.PMF;

import com.google.api.server.spi.config.Api;
import com.google.api.server.spi.config.ApiMethod;
import com.google.api.server.spi.config.ApiNamespace;
import com.google.api.server.spi.response.CollectionResponse;
import com.google.appengine.api.datastore.Cursor;
import com.google.appengine.datanucleus.query.JDOCursorHelper;

import java.util.HashMap;
import java.util.List;

import javax.annotation.Nullable;
import javax.inject.Named;
import javax.persistence.EntityExistsException;
import javax.persistence.EntityNotFoundException;
import javax.jdo.PersistenceManager;
import javax.jdo.Query;

@Api(name = "annotationendpoint", namespace = @ApiNamespace(ownerDomain = "mycompany.com", ownerName = "mycompany.com", packagePath = "services"))
public class AnnotationEndpoint {

	/**
	 * This method lists all the entities inserted in datastore.
	 * It uses HTTP GET method and paging support.
	 *
	 * @return A CollectionResponse class containing the list of all entities
	 * persisted and a cursor to the next page.
	 */
	@SuppressWarnings({ "unchecked", "unused" })
	@ApiMethod(name = "listAnnotation")
	public CollectionResponse<Annotation> listAnnotation(
			@Nullable @Named("cursor") String cursorString,
			@Nullable @Named("limit") Integer limit) {

		PersistenceManager mgr = null;
		Cursor cursor = null;
		List<Annotation> execute = null;

		try {
			mgr = getPersistenceManager();
			Query query = mgr.newQuery(Annotation.class);
			if (cursorString != null && cursorString != "") {
				cursor = Cursor.fromWebSafeString(cursorString);
				HashMap<String, Object> extensionMap = new HashMap<String, Object>();
				extensionMap.put(JDOCursorHelper.CURSOR_EXTENSION, cursor);
				query.setExtensions(extensionMap);
			}

			if (limit != null) {
				query.setRange(0, limit);
			}

			execute = (List<Annotation>) query.execute();
			cursor = JDOCursorHelper.getCursor(execute);
			if (cursor != null)
				cursorString = cursor.toWebSafeString();

			// Tight loop for fetching all entities from datastore and accomodate
			// for lazy fetch.
			for (Annotation obj : execute)
				;
		} finally {
			mgr.close();
		}

		return CollectionResponse.<Annotation> builder().setItems(execute)
				.setNextPageToken(cursorString).build();
	}

	/**
	 * This method gets the entity having primary key id. It uses HTTP GET method.
	 *
	 * @param id the primary key of the java bean.
	 * @return The entity with primary key id.
	 */
	@ApiMethod(name = "getAnnotation")
	public Annotation getAnnotation(@Named("id") Long id) {
		PersistenceManager mgr = getPersistenceManager();
		Annotation annotation = null;
		try {
			annotation = mgr.getObjectById(Annotation.class, id);
		} finally {
			mgr.close();
		}
		return annotation;
	}

	/**
	 * This inserts a new entity into App Engine datastore. If the entity already
	 * exists in the datastore, an exception is thrown.
	 * It uses HTTP POST method.
	 *
	 * @param annotation the entity to be inserted.
	 * @return The inserted entity.
	 */
	@ApiMethod(name = "insertAnnotation")
	public Annotation insertAnnotation(Annotation annotation) {
		PersistenceManager mgr = getPersistenceManager();
		try {
			if (containsAnnotation(annotation)) {
				throw new EntityExistsException("Object already exists");
			}
			mgr.makePersistent(annotation);
		} finally {
			mgr.close();
		}
		return annotation;
	}
	
	
	/**
	 * This method lists all the entities inserted in datastore with ascenceur annotation.
	 * It uses HTTP GET method and paging support.
	 *
	 * @return A CollectionResponse class containing the list of all entities which contain ascenceur
	 * persisted and a cursor to the next page.
	 */
	@ApiMethod(name = "listAnnotationByAsc")
	public CollectionResponse<Annotation> getAnnotationByAsc(
			@Named("ascenceur") Boolean asc,
			@Nullable @Named("cursor") String cursorString,
			@Nullable @Named("limit") Integer limit){
		
		PersistenceManager mgr = null;
		List<Annotation> execute = null;
		Cursor cursor = null;
		
		try {
			mgr = getPersistenceManager();
			Query query = mgr.newQuery(Annotation.class);
			query.setFilter("ascenceur == " + asc);
			//Query q = mgr.newQuery("select from Annotation" + "where ascenceur == " + asc);
			if (cursorString != null && cursorString != "") {
				cursor = Cursor.fromWebSafeString(cursorString);
				HashMap<String, Object> extensionMap = new HashMap<String, Object>();
				extensionMap.put(JDOCursorHelper.CURSOR_EXTENSION, cursor);
				query.setExtensions(extensionMap);
			}

			if (limit != null) {
				query.setRange(0, limit);
			}

			execute = (List<Annotation>) query.execute();
			cursor = JDOCursorHelper.getCursor(execute);
			if (cursor != null)
				cursorString = cursor.toWebSafeString();

			// Tight loop for fetching all entities from datastore and accomodate
			// for lazy fetch.
			for (Annotation obj : execute)
				;
		} finally {
			mgr.close();
		}

		return CollectionResponse.<Annotation> builder().setItems(execute)
				.setNextPageToken(cursorString).build();
	}
	

	/**
	 * This method is used for updating an existing entity. If the entity does not
	 * exist in the datastore, an exception is thrown.
	 * It uses HTTP PUT method.
	 *
	 * @param annotation the entity to be updated.
	 * @return The updated entity.
	 */
	@ApiMethod(name = "updateAnnotation")
	public Annotation updateAnnotation(Annotation annotation) {
		PersistenceManager mgr = getPersistenceManager();
		try {
			if (!containsAnnotation(annotation)) {
				throw new EntityNotFoundException("Object does not exist");
			}
			mgr.makePersistent(annotation);
		} finally {
			mgr.close();
		}
		return annotation;
	}

	/**
	 * This method removes the entity with primary key id.
	 * It uses HTTP DELETE method.
	 *
	 * @param id the primary key of the entity to be deleted.
	 */
	@ApiMethod(name = "removeAnnotation")
	public void removeAnnotation(@Named("id") Long id) {
		PersistenceManager mgr = getPersistenceManager();
		try {
			Annotation annotation = mgr.getObjectById(Annotation.class, id);
			mgr.deletePersistent(annotation);
		} finally {
			mgr.close();
		}
	}

	private boolean containsAnnotation(Annotation annotation) {
		PersistenceManager mgr = getPersistenceManager();
		boolean contains = true;
		try {
			mgr.getObjectById(Annotation.class, annotation.getId());
		} catch (javax.jdo.JDOObjectNotFoundException ex) {
			contains = false;
		} finally {
			mgr.close();
		}
		return contains;
	}

	private static PersistenceManager getPersistenceManager() {
		return PMF.get().getPersistenceManager();
	}

}
