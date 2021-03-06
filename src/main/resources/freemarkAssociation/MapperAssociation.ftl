<?xml version="1.0" encoding="UTF-8" ?>
<!DOCTYPE mapper PUBLIC "-//mybatis.org//DTD Mapper 3.0//EN" "http://mybatis.org/dtd/mybatis-3-mapper.dtd" >
<mapper namespace="<#if javaTable.classAssociationDAOStr??>${javaTable.classAssociationDAOPackage}<#else>${javaTable.classDAOPackage}</#if>">


<#--处理Vo 和关联关系-->
    <#list javaTables as javaTableTmp>
       <resultMap id="<#if javaTableTmp.classAssociationVoName??>${javaTableTmp.classAssociationVoName}<#else>${javaTableTmp.classVoName}</#if>" type="<#if javaTableTmp.classAssociationVoName??>${javaTableTmp.classAssociationVoPackage}<#else>${javaTable.classVoPackage}</#if>">
         <#list javaTableTmp.mysqlAndJavaFields as mysqlAndJavaField>
             <#if mysqlAndJavaField.mysqlField.isPRI == "true" >
                <id column="${mysqlAndJavaField.mysqlField.name}" property="${mysqlAndJavaField.javaField.name}"/>
             <#else>
                <result column="${mysqlAndJavaField.mysqlField.name}" property="${mysqlAndJavaField.javaField.name}"/>
             </#if>
         </#list>
         <#list javaTableTmp.associationHashMap?keys as key>
             <collection property="<#if javaTableTmp.associationHashMap[key].classAssociationVoName??>${javaTableTmp.associationHashMap[key].classAssociationVoName?uncap_first}<#else>${javaTableTmp.associationHashMap[key].classVoName?uncap_first}</#if>s" resultMap="<#if javaTableTmp.associationHashMap[key].classAssociationVoName??>${javaTableTmp.associationHashMap[key].classAssociationVoName}<#else>${javaTableTmp.associationHashMap[key].classVoName}</#if>"></collection>
         </#list>
       </resultMap>
    </#list>

    <select id="queryBase" resultMap="<#if javaTable.classAssociationVoStr??>${javaTable.classAssociationVoName}<#else>${javaTable.classVoName}</#if>">
        SELECT
      <#list javaTables as javaTableTmp>
          <#list javaTableTmp.mysqlTable.mysqlFields as field>
            `${javaTableTmp.mysqlTable.tableName}`.`${field.name}`<#if field_has_next>,</#if>
          </#list><#if javaTableTmp_has_next>,</#if>
      </#list>
        FROM
        `${javaTable.mysqlTable.tableName}`
        <#list javaTable.associations as association>
            JOIN `${association.rightTable}` ON `${association.leftTable}`.`${association.leftField}` = `${association.rightTable}`.`${association.rightField}`
        </#list>
        <where>
            1 = 1
            <#list associationJavaTables as associationJavaTable>
                <#list associationJavaTable.mysqlAndJavaFields as mysqlAndJavaField>
                <if test="${associationJavaTable.classVoName?uncap_first} != null and ${associationJavaTable.classVoName?uncap_first}.${mysqlAndJavaField.javaField.name} != null">
                    AND `${associationJavaTable.mysqlTable.tableName}`.`${mysqlAndJavaField.mysqlField.name}` = <#noparse>#{</#noparse>${associationJavaTable.classVoName?uncap_first}.${mysqlAndJavaField.javaField.name}<#noparse>}</#noparse>
                </if>
                </#list>
            </#list>

        </where>
    </select>

</mapper>