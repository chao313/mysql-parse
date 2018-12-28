<?xml version="1.0" encoding="UTF-8" ?>
<!DOCTYPE mapper PUBLIC "-//mybatis.org//DTD Mapper 3.0//EN" "http://mybatis.org/dtd/mybatis-3-mapper.dtd" >
<mapper namespace="${javaTable.classDAOPackage}">

     <insert id="insert"
            parameterType="${javaTable.classVoPackage}">
        INSERT INTO `${mysqlTable.tableName}`(
        <#list mysqlTable.mysqlFields as field>
          `${field.name}` <#if field_has_next>,</#if>
        </#list>
        )VALUE(
         <#list javaTable.javaFields as field>
             <#noparse>#{</#noparse>${field.name}<#noparse>}</#noparse>  <#if field_has_next>,</#if>
         </#list>
        )
     </insert>

     <update id="updateAllFieldByPrimaryKey"
            parameterType="${javaTable.classVoPackage}">
       UPDATE `${mysqlTable.tableName}`
        <set>
            <#list mysqlAndJavaFields as mysqlAndJavaField>
                `${mysqlAndJavaField.mysqlField.name}` = <#noparse>#{</#noparse>${mysqlAndJavaField.javaField.name}<#noparse>}</#noparse> <#if field_has_next>,</#if>
            </#list>
        </set>
        <where>
             <#list mysqlAndJavaKeys as mysqlAndJavaField>
                `${mysqlAndJavaField.mysqlField.name}` = <#noparse>#{</#noparse>${mysqlAndJavaField.javaField.name}<#noparse>}</#noparse> <#if field_has_next>,</#if>
             </#list>
        </where>
     </update>

     <update id="updateBaseFieldByPrimaryKey"
            parameterType="${javaTable.classVoPackage}">
     UPDATE `${mysqlTable.tableName}`
         <set>
            <#list mysqlAndJavaFields as mysqlAndJavaField>
                <if test="${mysqlAndJavaField.javaField.name} != null and ${mysqlAndJavaField.javaField.name} != '' ">
                    `${mysqlAndJavaField.mysqlField.name}` = <#noparse>#{</#noparse>${mysqlAndJavaField.javaField.name}<#noparse>}</#noparse> <#if field_has_next>,</#if>
                </if>
            </#list>
        </set>
       <where>
            <#list mysqlAndJavaKeys as mysqlAndJavaField>
                 `${mysqlAndJavaField.mysqlField.name}` = <#noparse>#{</#noparse>${mysqlAndJavaField.javaField.name}<#noparse>}</#noparse> <#if field_has_next>,</#if>
            </#list>
       </where>
     </update>

     <select id="queryByPrimaryKey"
             resultType="${javaTable.classVoPackage}">
           SELECT
              <#list mysqlTable.mysqlFields as field>
              `${field.name}` <#if field_has_next>,</#if>
              </#list>
           FROM `${mysqlTable.tableName}`
             <where>
                 <#list mysqlAndJavaKeys as mysqlAndJavaField>
                     `${mysqlAndJavaField.mysqlField.name}` = <#noparse>#{</#noparse>${mysqlAndJavaField.javaField.name}<#noparse>}</#noparse> <#if field_has_next>,</#if>
                 </#list>
             </where>
     </select>

    <select id="queryBase"
            resultType="${javaTable.classVoPackage}"
            parameterType="${javaTable.classVoPackage}">
        SELECT
              <#list mysqlTable.mysqlFields as field>
              `${field.name}` <#if field_has_next>,</#if>
              </#list>
        FROM `${mysqlTable.tableName}`
        <where>
             <#list mysqlAndJavaFields as mysqlAndJavaField>
                 <if test="${mysqlAndJavaField.javaField.name} != null and ${mysqlAndJavaField.javaField.name} != '' ">
                 `${mysqlAndJavaField.mysqlField.name}` = <#noparse>#{</#noparse>${mysqlAndJavaField.javaField.name}<#noparse>}</#noparse> <#if field_has_next>,</#if>
                 </if>
             </#list>
        </where>
    </select>

    <delete id="deleteByPrimaryKey">
        DELETE FROM `${mysqlTable.tableName}`
        <where>
          <#list mysqlAndJavaKeys as mysqlAndJavaField>
              `${mysqlAndJavaField.mysqlField.name}` = <#noparse>#{</#noparse>${mysqlAndJavaField.javaField.name}<#noparse>}</#noparse> <#if field_has_next>,</#if>
          </#list>
        </where>

    </delete>
    <delete id="deleteBase"
            parameterType="${javaTable.classVoPackage}">
        DELETE FROM `${mysqlTable.tableName}`
        <where>
          <#list mysqlAndJavaFields as mysqlAndJavaField>
           <if test="${mysqlAndJavaField.javaField.name} != null and ${mysqlAndJavaField.javaField.name} != '' ">
             `${mysqlAndJavaField.mysqlField.name}` = <#noparse>#{</#noparse>${mysqlAndJavaField.javaField.name}<#noparse>}</#noparse> <#if field_has_next>,</#if>
           </if>
          </#list>
        </where>
    </delete>

</mapper>