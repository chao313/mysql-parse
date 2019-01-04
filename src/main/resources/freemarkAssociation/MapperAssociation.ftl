<?xml version="1.0" encoding="UTF-8" ?>
<!DOCTYPE mapper PUBLIC "-//mybatis.org//DTD Mapper 3.0//EN" "http://mybatis.org/dtd/mybatis-3-mapper.dtd" >
<mapper namespace="${javaTable.classDAOPackage}">


<#--处理Vo 和关联关系-->
    <#list javaTables as javaTableTmp>
       <resultMap id="${javaTableTmp.classVoName}" type="${javaTable.classVoPackage}">
         <#list javaTableTmp.mysqlAndJavaFields as mysqlAndJavaField>
             <result column="${mysqlAndJavaField.mysqlField.name}" property="${mysqlAndJavaField.javaField.name}"/>
         </#list>
         <#list javaTableTmp.associationHashMap?keys as key>
             <collection property="${javaTableTmp.associationHashMap[key].classVoName?uncap_first}s" resultMap="${javaTableTmp.associationHashMap[key].classVoName?cap_first}"></collection>
         </#list>
       </resultMap>
    </#list>

    <select id="queryBase" resultMap="resultMap"
            resultType="${javaTable.classVoPackage}">
        SELECT
      <#list javaTables as javaTableTmp>
          <#list javaTableTmp.mysqlTable.mysqlFields as field>
            `${javaTableTmp.mysqlTable.tableName}`.`${field.name}`<#if field_has_next>,</#if>
          </#list>
      </#list>
        FROM
        <#list javaTable.associations as association>
            `${association.leftTable}` JOIN `${association.rightTable}` ON `${association.leftTable}`.`${association.leftTable}`.`${association.leftField}` = `${association.rightTable}`.`${association.rightField}`
        </#list>
        <where>
            1 = 1
        <#--<#list mysqlAndJavaFields as mysqlAndJavaField>-->
        <#--<if test="${mysqlAndJavaField.javaField.name} != null">-->
        <#--AND `${mysqlAndJavaField.mysqlField.name}` = <#noparse>-->
        <#--#{</#noparse>${mysqlAndJavaField.javaField.name}<#noparse>}</#noparse>-->
        <#--</if>-->
        <#--</#list>-->
        </where>
    </select>

</mapper>