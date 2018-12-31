<?xml version="1.0" encoding="UTF-8" ?>
<!DOCTYPE mapper PUBLIC "-//mybatis.org//DTD Mapper 3.0//EN" "http://mybatis.org/dtd/mybatis-3-mapper.dtd" >
<mapper namespace="${javaTable.classDAOPackage}">

    <resultMap id="resultMap" type="${javaTable.classVoPackage}">
         <#list mysqlAndJavaFields as mysqlAndJavaField>
             <result column="${mysqlAndJavaField.mysqlField.name}" property="${mysqlAndJavaField.javaField.name}"/>
         </#list>
    </resultMap>

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

    <insert id="inserts"
            parameterType="java.util.List">
        INSERT INTO `${mysqlTable.tableName}`(
        <#list mysqlTable.mysqlFields as field>
          `${field.name}` <#if field_has_next>,</#if>
        </#list>
        )VALUES
        <foreach collection="vos" item="item" separator=",">
            (
         <#list javaTable.javaFields as field>
             <#noparse>#{item.</#noparse>${field.name}<#noparse>}</#noparse>  <#if field_has_next>,</#if>
         </#list>
            )
        </foreach>
    </insert>

    <select id="queryBase" resultMap="resultMap"
            resultType="${javaTable.classVoPackage}"
            parameterType="${javaTable.classVoPackage}">
        SELECT
              <#list mysqlTable.mysqlFields as field>
              `${field.name}` <#if field_has_next>,</#if>
              </#list>
        FROM `${mysqlTable.tableName}`
        <where>
            1 =1
             <#list mysqlAndJavaFields as mysqlAndJavaField>
                 <if test="${mysqlAndJavaField.javaField.name} != null">
                     AND `${mysqlAndJavaField.mysqlField.name}` = <#noparse>#{</#noparse>${mysqlAndJavaField.javaField.name}<#noparse>}</#noparse>
                 </if>
             </#list>
        </where>
    </select>

    <update id="updateBase"
            parameterType="${javaTable.classVoPackage}">
        UPDATE `${mysqlTable.tableName}`
        <set>
            <#list mysqlAndJavaFields as mysqlAndJavaField>
                <#if mysqlAndJavaField.mysqlField.isPRI=false>
                  <if test="source.${mysqlAndJavaField.javaField.name} != null">
                   `${mysqlAndJavaField.mysqlField.name}` = <#noparse>#{source.</#noparse>${mysqlAndJavaField.javaField.name}<#noparse>}</#noparse><#if mysqlAndJavaField_has_next>,</#if>
                  </if>
               </#if>
            </#list>
        </set>
        <where>
            1 =1
             <#list mysqlAndJavaFields as mysqlAndJavaField>
             <if test="target.${mysqlAndJavaField.javaField.name} != null">
                 AND `${mysqlAndJavaField.mysqlField.name}` = <#noparse>#{target.</#noparse>${mysqlAndJavaField.javaField.name}<#noparse>}</#noparse>
             </if>
             </#list>
        </where>
    </update>

    <update id="updateBaseIncludeNull"
            parameterType="${javaTable.classVoPackage}">
        UPDATE `${mysqlTable.tableName}`
        <set>
            <#list mysqlAndJavaFields as mysqlAndJavaField>
                <#if mysqlAndJavaField.mysqlField.isPRI=false>
                   `${mysqlAndJavaField.mysqlField.name}` = <#noparse>#{source.</#noparse>${mysqlAndJavaField.javaField.name}<#noparse>}</#noparse><#if mysqlAndJavaField_has_next>,</#if>
                </#if>
            </#list>
        </set>
        <where>
            1 =1
             <#list mysqlAndJavaFields as mysqlAndJavaField>
             <if test="target.${mysqlAndJavaField.javaField.name} != null">
                 AND `${mysqlAndJavaField.mysqlField.name}` = <#noparse>#{target.</#noparse>${mysqlAndJavaField.javaField.name}<#noparse>}</#noparse>
             </if>
             </#list>
        </where>
    </update>

    <delete id="deleteBase"
            parameterType="${javaTable.classVoPackage}">
        DELETE FROM `${mysqlTable.tableName}`
        <where>
            1 =1
               <#list mysqlAndJavaFields as mysqlAndJavaField>
                <if test="${mysqlAndJavaField.javaField.name} != null">
                    AND `${mysqlAndJavaField.mysqlField.name}` = <#noparse>#{</#noparse>${mysqlAndJavaField.javaField.name}<#noparse>}</#noparse>
                </if>
               </#list>
        </where>
    </delete>

<#if javaTable.primaryKeys?? && (javaTable.primaryKeys?size>0) >

     <select id="queryByPrimaryKey" resultMap="resultMap"
             resultType="${javaTable.classVoPackage}">
         SELECT
              <#list mysqlTable.mysqlFields as field>
              `${field.name}` <#if field_has_next>,</#if>
              </#list>
         FROM `${mysqlTable.tableName}`
         <where>
             1 =1
                 <#list mysqlAndJavaKeys as mysqlAndJavaField>
                   AND `${mysqlAndJavaField.mysqlField.name}` = <#noparse>#{</#noparse>${mysqlAndJavaField.javaField.name}<#noparse>}</#noparse>
                 </#list>
         </where>
     </select>



    <delete id="deleteByPrimaryKey">
        DELETE FROM `${mysqlTable.tableName}`
        <where>
            1 =1
              <#list mysqlAndJavaKeys as mysqlAndJavaField>
                 AND `${mysqlAndJavaField.mysqlField.name}` = <#noparse>#{</#noparse>${mysqlAndJavaField.javaField.name}<#noparse>}</#noparse>
              </#list>
        </where>

    </delete>
</#if>

</mapper>