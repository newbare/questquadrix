/*package com.br.questquadirx.util.infra;
package com.br.questquadirx.util.infra;

 

 import java.beans.IntrospectionException;
import java.io.PrintStream;
import java.io.Serializable;
import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import javax.inject.Named;
import javax.persistence.EmbeddedId;
import javax.persistence.EntityManager;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.NoResultException;
import javax.persistence.OneToOne;
import javax.persistence.OrderBy;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.Transient;

 import org.apache.commons.beanutils.PropertyUtils;
 import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Repository;

import com.br.questquadirx.util.jpa.Dao;

@Named
 public class JpaDao<T>  implements Dao<T>
 {
   private static final long serialVersionUID = 6907111403428623938L;
   private Class<T> persistentClass;
 
   @PersistenceContext
   private EntityManager entityManager;
 
   
 
   private Integer maxResults;
   private Integer startingFrom;
   private String stringHint;
   private boolean booleanHint;
 
   public String countFindByFilters(Object[] filters)
   {
       Query query = createQueryFilter(filters, true);
       setHint(query);
       if (getMaxResults() != null) {
         query.setMaxResults(getMaxResults().intValue());
     }
       if (getStartingFrom() != null) {
         query.setFirstResult(getStartingFrom().intValue());
     }
       return query.getSingleResult().toString();
   }
 
   private Query createByNamedQuery(String namedQuery, Paginacao paginacao, Object[] params) {
       Query query = this.entityManager.createNamedQuery(namedQuery);
       setHint(query);
       setQueryParams(query, params);
       paginar(paginacao, query);
       return query;
   }
 
   private Query createNativeQuery(String sql, Object[] params) {
       Query query = this.entityManager.createNativeQuery(sql);
       setHint(query);
       setQueryParams(query, params);
       return query;
   }
 
   private Query createQuery(String queryStr, Paginacao paginacao, Object[] params) {
       Query query = this.entityManager.createQuery(queryStr);
       setHint(query);
       setQueryParams(query, params);
       paginar(paginacao, query);
       return query;
   }
 
   private Query createQueryFilter(Object[] filters, boolean count) {
       StringBuilder qsb = null;
       if (count)
         qsb = new StringBuilder("select count(*) from ");
     else {
         qsb = new StringBuilder("select xxx from ");
     }
       StringBuilder orderBy = new StringBuilder();
       qsb.append(getPersistentClass().getSimpleName());
       qsb.append(" as xxx ");
       Map params = new HashMap();
       FilterOperator op = null;
       int filterCount = 0;
       int paramCount = 0;
     int i;
       if (filters != null) {
         for (i = 0; i < filters.length; ) {
           Object filter = filters[(i++)];
           if (filterCount > 0) {
             if (filter instanceof FilterOperator)
               if (filter.equals(FilterOperator.PAR_IN)) {
                 qsb.append(" and ( ");
                 filter = filters[(i++)];
               } else if (filter.equals(FilterOperator.PAR_OUT)) {
                 qsb.append(" ) ");
                 if (i >= filters.length) break;
                 filter = filters[(i++)];
                 if (filter.equals(FilterOperator.PAR_IN)) {
                   qsb.append(" and ( ");
                   filter = filters[(i++)];
               } else {
                   qsb.append(" and ");
               }
 
             }
               else if (filter.equals(FilterOperator.OR)) {
                 qsb.append(" or");
                 filter = filters[(i++)];
               } else if (filter.equals(FilterOperator.TO_CHAR)) {
                 qsb.append(" and TO_CHAR( ");
                 filter = filters[(i++)];
               } else if (filter.equals(FilterOperator.TO_CHAR_DATE_OUT)) {
                 qsb.append(" ,'DD/MM/YYYY') ");
                 filter = filters[(i++)];
             }
           else
               qsb.append(" and");
         }
         else {
             qsb.append(" where");
         }
           if (getPersistentClass().isInstance(filter)) {
             Map attrs = new HashMap();
           try {
               filterCount += mapJpaObject(filter, attrs, null, false, 0);
           } catch (Exception e) {
               System.err.println("error.query.builder");
               System.err.println(qsb.toString());
               throw new RuntimeException("error.query.builder");
           }
             qsb.append(findByAttrs(attrs, params));
         }
         else {
             if (filter instanceof FilterOperator) {
               op = (FilterOperator)filter;
           } else {
               qsb.append(" xxx.");
               qsb.append(filter);
               Object oop = filters[(i++)];
               if (oop instanceof FilterOperator) {
                 op = (FilterOperator)oop;
             } else {
                 System.err.println("error.query.builder");
                 System.err.println(oop);
                 System.err.println(qsb.toString());
                 throw new RuntimeException("error.query.builder");
             }
           }
             qsb.append(" ");
             qsb.append(op.getStringValue());
             if (op != null) {
               if (op.isUseParenthesis()) {
                 qsb.append("(");
             }
             int np = op.getNumParams();
            while (np-- > 0) {
              params.put("param_" + (++paramCount), filters[(i++)]);
               qsb.append(" :param_" + paramCount);
               if (np > 0);
               qsb.append(" and ");
             }
 
            if (op.isUseParenthesis()) {
              qsb.append(")");
             }
             ++filterCount;
           }
         }
       }
     }
   if (getPersistentClass() != null) {
       Map attrs = new HashMap();
       try {
         filterCount += mapJpaObjectOrderBy(getPersistentClass(), attrs, null);
       } catch (Exception e) {
         System.err.println("error.query.builder");
        System.err.println(qsb.toString());
        e.printStackTrace();
        throw new RuntimeException("error.query.builder");
       }
    findByAttrsOrderBy(attrs, orderBy);
     }
     qsb.append(orderBy);
    Query q = this.entityManager.createQuery(qsb.toString());
     setQueryParams(q, params);
    return q;
   }
 
   private List<?> createQueryHql(String queryStr, Paginacao paginacao, Map<String, Object> params) {
     Query query = this.entityManager.createQuery(queryStr);
    setHint(query);
     setQueryParams(query, params);
     paginar(paginacao, query);
    return query.getResultList();
   }
 
   public void delete(Serializable id)
   {

     this.entityManager.remove(findById(id));
   }
 
   public List<?> find(String queryStr, Object[] params)
   {
     return find(queryStr, null, params);
   }
 
   public List<?> find(String queryStr, Paginacao paginacao, Object[] params)
   {
     return createQuery(queryStr, paginacao, params).getResultList();
   }
 
   public List<T> findAll()
   {
     return findAll(null);
   }
 
   public List<T> findAll(Paginacao paginacao)
   {
     Query query = createQueryFilter(null, false);
     setHint(query);
    paginar(paginacao, query);
     return query.getResultList();
   }
 
   private String findByAttrs(Map<String, JpaDao<T>.FindParam> attrs, Map<String, Object> params)
   {
     StringBuilder jpql = new StringBuilder();
          int i;
   if ((attrs != null) && (attrs.size() > 0)) {
          
      
         i = 0;
         for (Map.Entry entry : attrs.entrySet()) {
           if (!(isEmpty(((FindParam)entry.getValue()).value, ((FindParam)entry.getValue()).type))) {
             if (i++ > 0) {
               jpql.append(" and");
                }
             if ((((FindParam)entry.getValue()).usaLike) && (((FindParam)entry.getValue()).type == String.class) && (!(((FindParam)entry.getValue()).key)))
                {
               jpql.append(" upper(xxx.");
               jpql.append((String)entry.getKey());
               jpql.append(")");
      
               jpql.append(" like ");
      
               jpql.append("upper(");
               jpql.append(":");
               jpql.append(key2ParamName((String)entry.getKey()));
               jpql.append(")");
      
               //FindParam.acce((FindParam)entry.getValue(), "%" + ((FindParam)entry.getValue()).value + "%");
              } else {
               jpql.append(" xxx.");
               jpql.append((String)entry.getKey());
               jpql.append(" = ");
               jpql.append(" :");
               jpql.append(key2ParamName((String)entry.getKey()));
                }
             params.put(key2ParamName((String)entry.getKey()), ((FindParam)entry.getValue()).value);
              }
            }
          }
       return jpql.toString();
 
	}
 
   private String findByAttrsOrderBy(Map<String, JpaDao<T>.FindParam> attrs, StringBuilder orderBy) {
       StringBuilder jpql = new StringBuilder();
     if ((attrs != null) && (attrs.size() > 0)) {
       
      
        List orderBys = new ArrayList();
        for (Map.Entry entry : attrs.entrySet()) {        
        	if ((((FindParam)entry.getValue()).orderByAsc) || (((FindParam)entry.getValue()).orderByDesc)) {
            orderBys.add(entry.getValue());
              }
            }
        Collections.sort(orderBys);
       for (FindParam param : orderBys) {
          if (orderBy.length() == 0) {
            orderBy.append(" order by ");
            orderBy.append(" xxx.");
            orderBy.append(param.name);
              } else {
            orderBy.append(", xxx.");
            orderBy.append(param.name);
              }
          if (param.orderByAsc)
            orderBy.append(" ASC ");
          else if (param.orderByDesc) {
            orderBy.append(" DESC ");
              }
            }
          }
      return jpql.toString();

	}
 
   public List<T> findByFilters(Object[] filters)
   {
    Query query = createQueryFilter(filters, false);
     setHint(query);
    if (getMaxResults() != null) {
       query.setMaxResults(getMaxResults().intValue());
     }
    if (getStartingFrom() != null) {
      query.setFirstResult(getStartingFrom().intValue());
     }
    return query.getResultList();
   }
 
   public T findById(Serializable id)
   {
	   return this.entityManager.find(getPersistentClass(), id);
   }
 
   public List<T> findByNamedParams(String queryname, Map<String, Object> params)
   {
   Query query = this.entityManager.createNamedQuery(queryname);
    setHint(query);
     setQueryParams(query, params);
     return query.getResultList();
   }
 
   public List<T> findByNamedParams(String queryname, Paginacao paginacao, Map<String, Object> params)
   {
    Query query = this.entityManager.createNamedQuery(queryname);
     setHint(query);
     setQueryParams(query, params);
     paginar(paginacao, query);
     return query.getResultList();
   }
 
   public List<?> findByNamedParamsHql(String queryStr, Map<String, Object> params)
   {
     return createQueryHql(queryStr, null, params);
   }
 
   public List<T> findByNamedQuery(String namedQuery, Object[] params)
   {
     return findByNamedQuery(namedQuery, null, params);
   }
 
   public List<T> findByNamedQuery(String namedQuery, Paginacao paginacao, Object[] params)
   {
    Query query = createByNamedQuery(namedQuery, paginacao, params);
    return query.getResultList();
   }
 
   public List<T> findByNativeQuery(String sql, Object[] params)
   {
    return findByNativeQuery(sql, null, params);
   }
 
   public List<?> findObjectsByNativeQuery(String sql, Object[] params)
   {
     return findByNativeQuery(sql, null, params);
   }
 
   public List<T> findByNativeQuery(String sql, Paginacao paginacao, Object[] params)
   {
    Query query = createNativeQuery(sql, params);
    paginar(paginacao, query);
    return query.getResultList();
   }
 
   public List<T> findByNativeQueryNamedParams(String sql, Map<String, Object> params)
   {
     return findByNativeQueryNamedParams(sql, null, params);
   }
 
   public List<T> findByNativeQueryNamedParams(String sql, Paginacao paginacao, Map<String, Object> params)
   {
     Query query = this.entityManager.createNativeQuery(sql);
     setHint(query);
     setQueryParams(query, params);
     paginar(paginacao, query);
    return query.getResultList();
   }
 
   public Object findSingleResult(String queryStr, Object... params) {
     try {
       return createQuery(queryStr, null, params).getSingleResult(); } catch (NoResultException e) {
     }
     return null;
   }
 
   public Object findSingleResultByNamedQuery(String namedQuery, Object[] params)
   {
     try {
       return createByNamedQuery(namedQuery, null, params).getSingleResult(); } catch (NoResultException e) {
     }
     return null;
   }
 
   public EntityManager getEntityManager()
   {
     return this.entityManager;
   }
 
   private <T extends Annotation> T getFieldAnnotation(Field field, Class<T> class1)
   {
          try {
       Annotation ann = field.getAnnotation(class1);
         if (ann == null) {
           Class cls = field.getDeclaringClass();
           String name = ((field.getType() == Boolean.TYPE) ? "is" : "get") + field.getName().substring(0, 1).toUpperCase() + field.getName().substring(1);
              try
              {
             Method method = cls.getDeclaredMethod(name, new Class[0]);
             ann = method.getAnnotation(class1);
              } catch (Exception e) {
              }
            }
         return (T) ann;
          } catch (Exception e) {
         throw new RuntimeException(e);
          }
       
	}
 
   public Integer getMaxResults()
   {
     return this.maxResults;
   }
 
   public final Class<T> getPersistentClass() {
     if (this.persistentClass == null) {
      throw new RuntimeException("É necessário invocar o método setPersistentClass(Class<T> clazz)");
     }
     return this.persistentClass;
   }
 
   public Integer getStartingFrom()
   {
     return this.startingFrom;
   }
 
   private boolean isEmpty(Object value, Class<?> type) {
    if (value instanceof String)
       return ((String)value).isEmpty();
    if (value instanceof Long)
       return value.equals(Long.valueOf(0L));
     if (value instanceof Double)
      return value.equals(Double.valueOf(0.0D));
    if (value instanceof Float)
      return value.equals(Float.valueOf(0.0F));
    if (value instanceof Number) {
       return value.equals(Integer.valueOf(0));
     }
     return (value == null);
   }
 
   private String key2ParamName(String key) {
     return key.replaceAll("[.]", "_");
   }
 
   private int mapJpaObject(Object obj, Map<String, JpaDao<T>.FindParam> map, String alias, boolean idObj, int orderN)
     throws IllegalAccessException, InvocationTargetException, NoSuchMethodException, IntrospectionException
   {
       int filterCount = 0;
       Field[] fields = obj.getClass().getDeclaredFields();
       String aliasprefix = (alias != null) ? alias + "." : "";
       for (Field field : fields) {
         Transient transien = (Transient)field.getAnnotation(Transient.class);
         if (((field.getModifiers() & 0x8) == 8) || ((field.getModifiers() & 0x80) == 128) || (transien != null)) {
              continue;
            }
         Object value = PropertyUtils.getSimpleProperty(obj, field.getName());
         OrderBy orderBy = (OrderBy)getFieldAnnotation(field, OrderBy.class);
      
         if ((value != null) && (!(value instanceof Collection)) && (!(isEmpty(value, field.getType())))) {
           ManyToOne manyToOne = (ManyToOne)getFieldAnnotation(field, ManyToOne.class);
           OneToOne oneToOne = (OneToOne)getFieldAnnotation(field, OneToOne.class);
           EmbeddedId embId = (EmbeddedId)getFieldAnnotation(field, EmbeddedId.class);
           Id id = (Id)getFieldAnnotation(field, Id.class);
           LikeField usaContains = (LikeField)getFieldAnnotation(field, LikeField.class);
           String keyName = aliasprefix + field.getName();
      
           if ((manyToOne != null) || (embId != null) || (oneToOne != null))
              {
             filterCount += mapJpaObject(value, map, aliasprefix + field.getName(), embId != null, orderN + 1);
              }
              else
              {
             map.put(keyName, new FindParam(keyName, field.getType(), value, (idObj) || (id != null), usaContains != null, (orderBy != null) ? orderBy.value().equals("ASC") : false, (orderBy != null) ? orderBy.value().equals("DESC") : false, orderN));
      
             ++filterCount;
              }
         } else if ((orderBy != null) && (!(value instanceof Collection))) {
           String keyName = aliasprefix + field.getName();
           map.put(keyName, new FindParam(keyName, null, null, false, false, !(orderBy.value().equals("DESC")), (orderBy != null) ? orderBy.value().equals("DESC") : false, orderN));
            }
      
          }
      
       return filterCount;
   return 0;}
   private int mapJpaObjectOrderBy(Class<?> clss, Map<String, JpaDao<T>.FindParam> map, String alias)
     throws IllegalAccessException, InvocationTargetException, NoSuchMethodException, IntrospectionException
   {
       int filterCount = 0;
       Field[] fields = clss.getDeclaredFields();
       String aliasprefix = (alias != null) ? alias + "." : "";
       for (Field field : fields)
          {
         Transient transien = (Transient)getFieldAnnotation(field, Transient.class);
         if (((field.getModifiers() & 0x8) == 8) || ((field.getModifiers() & 0x80) == 128) || (transien != null)) {
              continue;
            }
         OrderBy orderBy = (OrderBy)getFieldAnnotation(field, OrderBy.class);
      
         EmbeddedId embId = (EmbeddedId)getFieldAnnotation(field, EmbeddedId.class);
         if ((orderBy != null) && (!(Collection.class.isAssignableFrom(field.getType())))) {
           String keyName = aliasprefix + field.getName();
           map.put(keyName, new FindParam(keyName, null, null, false, false, !(orderBy.value().equals("DESC")), (orderBy != null) ? orderBy.value().equals("DESC") : false, 0));
            }
         else if (embId != null) {
           mapJpaObjectOrderBy(field.getType(), map, aliasprefix + field.getName());
            }
          }
      
       return filterCount;
       
    	 return 0;}
 
   private void paginar(Paginacao paginacao, Query query)
   {
    if (paginacao != null) {
      if (paginacao.getPosicao() != null) {
         query.setFirstResult(paginacao.getPosicao().intValue());
       }
      if (paginacao.getLimite() != null)
         query.setMaxResults(paginacao.getLimite().intValue());
     }
   }
 
   public void refresh(T entity)
   {
    this.entityManager.refresh(entity);
   }
 
   public void save(T obj)
   {
     
     this.entityManager.persist(obj);
   }
 
   private void setHint(Query query)
   {
     if ((this.booleanHint) && (this.stringHint != null) && (!(this.stringHint.trim().isEmpty())))
       query.setHint(this.stringHint, Boolean.valueOf(this.booleanHint));
   }
 
   public void setHint(String stringHint, boolean booleanHint)
   {
     this.stringHint = stringHint;
    this.booleanHint = booleanHint;
   }
 
   public void setMaxResults(Integer maxResults)
   {
     this.maxResults = maxResults;
   }
 
   public final void setPersistentClass(Class<T> persistentClass) {
     this.persistentClass = persistentClass;
   }
 
   private void setQueryParams(Query query, Map<String, Object> params)
   {
     for (Map.Entry entry : params.entrySet())
       query.setParameter((String)entry.getKey(), entry.getValue());
   }
 
   private void setQueryParams(Query query, Object[] params)
   {
     if ((params != null) && (params.length > 0))
      for (int i = 0; i < params.length; ++i)
        query.setParameter(i + 1, params[i]);
   }
 

   public void setStartingFrom(Integer startingFrom)
   {
    this.startingFrom = startingFrom;
   }
 
   public void update(T obj)
   {

     this.entityManager.merge(obj);
   }
 
   public void updateWithNativeQuery(String sql, Object[] params)
   {

   createNativeQuery(sql, params).executeUpdate();
   }
 
   private class FindParam    implements Comparable<JpaDao<T>.FindParam>
   {
     private String name;
     private Class<?> type;
     private Object value;
     private boolean key;
     private boolean usaLike;
     private boolean orderByAsc;
     private boolean orderByDesc;
     private int orderN;
 
     public FindParam(Class<?> name, Object type, boolean value, boolean key, boolean usaLike, boolean orderByAsc, int orderByDesc)
          {
          this.name = name.toString();
          this.type = (Class<?>) type;
          this.value = value;
          this.key = key;
          this.usaLike = usaLike;
          this.orderByAsc = orderByAsc;
         // this.orderByDesc = orderByDesc;
          this.orderN = orderN;
          }
 
     public int compareTo(JpaDao<T>.FindParam o)
     {
      return (this.orderN - o.orderN);
     }
   }
 }

*/