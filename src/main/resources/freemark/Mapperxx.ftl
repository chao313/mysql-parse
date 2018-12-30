<?xml version="1.0" encoding="UTF-8" ?>
<!DOCTYPE mapper PUBLIC "-//mybatis.org//DTD Mapper 3.0//EN" "http://mybatis.org/dtd/mybatis-3-mapper.dtd" >
<mapper namespace="${javaTable.classDAOPackage}">
    <resultMap id="truckExitInfo" type="com.sgx.inspect.entity.sequence.RoadNetOut">
        <id column="exit_record_no" property="exitRecordId" />
        <result column="total_toll" property="totalToll"/>
        <result column="p_discount_toll" property="pDiscountToll"/>
        <result column="prealAmount" property="prealAmount"/>
        <result column="free_kind" property="freeKind"/>
        <result column="over_load_rate" property="overLoadRate"/>
        <result column="pay_method" property="payMethod"/>
        <result column="total_weight" property="weight"/>
    </resultMap>

    <insert id="insert"
            parameterType="${javaTable.classVoPackage}">
        INSERT INTO `${mysqlTable.tableName}`(
        <#list mysqlTable.javaFields as field>
          `${field.name}` <#if field_has_next>,</#if>
        </#list>
        )VALUE(
         <#list javaTable.classVoPackage as field>
          #{${field.name}} <#if field_has_next>,</#if>
         </#list>
        )
    </insert>


    <insert id="insert"
            parameterType="java.util.List">
        INSERT INTO `ts_eutrain_customs_declaration_bind`(
        `id`,
        `TASK_ID`,
        `gross_weights`,
        `net_weights`,
        `trade_State`,
        `send_date`,
        `customs_declaration_no`,
        `customs_declaration_url`,
        `waybill_url`,
        `create_time`,
        `update_time`,
        `create_user_id`,
        `is_deleted`,
        `delete_user_id`,
        `status`,
        `CONTAINER1`,
        `CONTAINER2`,
        `remark`
        )VALUES

        <foreach collection="vos" item="item" separator=",">
            (
        #{item.id},
        #{item.taskId},
        #{item.grossWeights},
        #{item.netWeights},
        #{item.tradeState},
        #{item.sendDate},
        #{item.customsDeclarationNo},
        #{item.customsDeclarationUrl},
        #{item.waybillUrl},
        #{item.createTime},
        #{item.updateTime},
        #{item.createUserId},
        #{item.isDeleted},
        #{item.deleteUserId},
        #{item.status},
        #{item.container1},
        #{item.container2},
        #{item.remark}
            )
        </foreach>
    </insert>


    <update id="updateBaseById"
            parameterType="cn.jssgx.prealtruck.foundation.domain.model.eutrain.EutrainCustomsDeclarationBind">
        update `ts_eutrain_customs_declaration_bind`

        <set>
            <if test="taskId != null and taskId != '' ">
                `TASK_ID` = #{taskId} ,
            </if>
            <if test="grossWeights != null and grossWeights != '' ">
                `gross_weights` = #{grossWeights} ,
            </if>
            <if test="netWeights != null and netWeights != '' ">
                `net_weights` = #{netWeights} ,
            </if>
            <if test="tradeState != null and tradeState != '' ">
                `trade_state` = #{tradeState} ,
            </if>
            <if test="sendDate != null and sendDate != '' ">
                `send_date` = #{sendDate} ,
            </if>
            <if test="customsDeclarationUrl != null and customsDeclarationUrl != '' ">
                `customs_declaration_url` = #{customsDeclarationUrl} ,
            </if>
            <if test="customsDeclarationNo != null and customsDeclarationNo != '' ">
                `customs_declaration_no` = #{customsDeclarationNo} ,
            </if>
            <if test="waybillUrl != null and waybillUrl != '' ">
                `waybill_url` = #{waybillUrl} ,
            </if>

            <if test="createTime != null and createTime != '' ">
                `create_time` = #{createTime} ,
            </if>
            <if test="updateTime != null and updateTime != '' ">
                `update_time` = #{updateTime} ,
            </if>

            <if test="createUserId != null and createUserId != '' ">
                `create_user_id` = #{createUserId} ,
            </if>
            <if test="isDeleted != null ">
                `is_deleted` = #{isDeleted} ,
            </if>
            <if test="status != null and status != '' ">
                `status` = #{status} ,
            </if>
            <if test="deleteUserId != null and deleteUserId != '' ">
                `delete_user_id` = #{deleteUserId} ,
            </if>
            <if test="container1 != null and container1 != '' ">
                `container1` = #{container1} ,
            </if>
            <if test="container2 != null and container2 != '' ">
                `container2` = #{container2} ,
            </if>
        </set>
        where id = #{id}
    </update>

    <select id="queryGroup"
            resultType="cn.jssgx.prealtruck.foundation.domain.model.eutrain.EutrainCustomsDeclarationBindGroup">
        SELECT GROUP_CONCAT(distinct bind.`id`) AS id ,
        bind.`TASK_ID` AS taskId,
        bind.`status` AS status,
        GROUP_CONCAT( distinct bind.`trade_state`) AS tradeState,
        IF(bind.`CONTAINER2` = '' ,GROUP_CONCAT(distinct bind.`customs_declaration_no`) ,
        GROUP_CONCAT(distinct bind.`customs_declaration_no`)) AS customsDeclarationNo,
        IF(bind.`CONTAINER2` = '' ,GROUP_CONCAT(distinct bind.`CONTAINER1`) ,
        CONCAT(bind.`CONTAINER1` , ",",bind.`CONTAINER2`)) AS container,
        journey.`VALIDITY_END_TIME` AS endTime,
        journey.`CONTAINER_NUM` AS containerAmount ,
        subtype.SUBTYPE_NAME AS transpGenreName,
        truck.`PLATE_NO` AS plateNo,
        if ( task_info.GENRE_ID = 0 , "去程" , if(task_info.GENRE_ID = 1, "来程" , "双程")) AS direction
        FROM `ts_eutrain_customs_declaration_bind` AS bind
        INNER JOIN ts_transportationtask_journey_info AS journey ON bind.`TASK_ID` = journey.`TASK_ID`
        INNER JOIN ts_transportationtask_info AS task_info ON bind.`TASK_ID` = task_info.`TASK_ID`
        INNER JOIN ts_preference_type_subtype AS subtype ON task_info.`SUBTYPE_ID` = subtype.`SUBTYPE_ID`
        INNER JOIN ts_preferentialtruck_info AS truck ON task_info.TRUCK_ID = truck.TRUCK_ID
        WHERE bind.`is_deleted` = 1
        <if test="status.size() >0">
            AND bind.`status` IN
            <foreach collection="status" index="index" item="item" open="(" separator="," close=")">
            #{item}
            </foreach>
        </if>
        <if test="plateNo != null and plateNo != '' ">
            AND truck.`PLATE_NO` LIKE CONCAT('%', #{plateNo}, '%')
        </if>
        <if test="customsDeclarationNo != null and customsDeclarationNo != '' ">
            AND ( bind.`customs_declaration_no` LIKE CONCAT('%', #{customsDeclarationNo}, '%') )
        </if>
        GROUP BY bind.`TASK_ID`
        ORDER BY bind.update_time DESC
        <if test="start != null and start != '' and  end != null and end != '' and start > 0 and end > start ">
            LIMIT ${start-1}, ${end-start+1}
        </if>

    </select>

    <select id="queryGroupCount"
            resultType="java.lang.Integer">
        SELECT count(1) FROM (
        SELECT
        count(1) as sum
        FROM `ts_eutrain_customs_declaration_bind` AS bind
        INNER JOIN ts_transportationtask_journey_info AS journey ON bind.`TASK_ID` = journey.`TASK_ID`
        INNER JOIN ts_transportationtask_info AS task_info ON bind.`TASK_ID` = task_info.`TASK_ID`
        INNER JOIN ts_preference_type_subtype AS subtype ON task_info.`SUBTYPE_ID` = subtype.`SUBTYPE_ID`
        INNER JOIN ts_preferentialtruck_info AS truck ON task_info.TRUCK_ID = truck.TRUCK_ID
        WHERE bind.`is_deleted` = 1
        <if test="status.size() >0">
            AND bind.`status` IN
            <foreach collection="status" index="index" item="item" open="(" separator="," close=")">
            #{item}
            </foreach>
        </if>
        <if test="plateNo != null and plateNo != '' ">
            AND truck.`PLATE_NO` LIKE CONCAT('%', #{plateNo}, '%')
        </if>
        <if test="customsDeclarationNo != null and customsDeclarationNo != '' ">
            AND ( bind.`customs_declaration_no` LIKE CONCAT('%', #{customsDeclarationNo}, '%') )
        </if>
        GROUP BY bind.`TASK_ID`
        ) AS tmp
    </select>


    <select id="queryByIds"
            parameterType="java.util.List"
            resultType="cn.jssgx.prealtruck.foundation.domain.model.eutrain.EutrainCustomsDeclarationBindAndTask">

        SELECT
        bind.`id` AS id,
        bind.`TASK_ID` AS taskId,
        bind.`gross_weights` AS grossWeights,
        bind.`net_weights` AS netWeights,
        bind.`send_Date` AS sendDate,
        bind.`trade_state` AS tradeState,
        bind.`customs_declaration_no` AS customsDeclarationNo,
        bind.`customs_declaration_url` AS customsDeclarationUrl,
        bind.`waybill_url` AS waybillUrl,
        bind.`create_time` AS createTime,
        bind.`update_time` AS updateTime,
        bind.`create_user_id` AS createUserId,
        bind.`is_deleted` AS isDeleted,
        bind.`delete_user_id` AS deleteUserId,
        bind.`status` AS status,
        bind.`CONTAINER1` AS container1,
        bind.`CONTAINER2` AS container2,
        bind.`remark` AS remark
        from `ts_eutrain_customs_declaration_bind` bind
        where id in
        <foreach collection="list" index="index" item="item" open="(" separator="," close=")">
        #{item}
        </foreach>
    </select>


    <select id="queryBase"
            parameterType="cn.jssgx.prealtruck.foundation.domain.model.eutrain.EutrainCustomsDeclarationBindQuery"
            resultType="cn.jssgx.prealtruck.foundation.domain.model.eutrain.EutrainCustomsDeclarationBindAndTask">
        SELECT
        bind.`id` AS id,
        bind.`TASK_ID` AS taskId,
        bind.`gross_weights` AS grossWeights,
        bind.`net_weights` AS netWeights,
        bind.`send_Date` AS sendDate,
        bind.`trade_state` AS tradeState
        bind.`customs_declaration_no` AS customsDeclarationNo,
        bind.`customs_declaration_url` AS customsDeclarationUrl,
        bind.`waybill_url` AS waybillUrl,
        bind.`create_time` AS createTime,
        bind.`update_time` AS updateTime,
        bind.`create_user_id` AS createUserId,
        bind.`is_deleted` AS isDeleted,
        bind.`delete_user_id` AS deleteUserId,
        bind.`status` AS status,
        bind.`CONTAINER1` AS container1,
        bind.`CONTAINER2` AS container2,

        journey.`VALIDITY_END_TIME` AS endTime, --有效期结束时间
        journey.CONTAINER_NUM AS containerAmount --集装箱数量
        truck.`PLATE_NO` as plateNo --车牌号
        FROM `ts_eutrain_customs_declaration_bind` bind
        INNER JOIN ts_transportationtask_journey_info journey ON bind.`TASK_ID` = journey.`TASK_ID`
        INNER JOIN ts_transportationtask_info task_info ON bind.`TASK_ID` = task_info.`TASK_ID`
        INNER JOIN ts_preference_type_subtype subtype ON task_info.`SUBTYPE_ID` = subtype.`SUBTYPE_ID`
        INNER JOIN ts_preferentialtruck_info truck ON task_info.TRUCK_ID = truck.TRUCK_ID

        <if test="taskId != null and taskId != '' ">
            AND bind.`TASK_ID` = #{taskId}
        </if>
        <if test="grossWeights != null and grossWeights != '' ">
            AND bind.`gross_weights` = #{grossWeights}
        </if>
        <if test="netWeights != null and netWeights != '' ">
            AND bind.`net_weights` = #{netWeights}
        </if>
        <if test="customsDeclarationUrl != null and customsDeclarationUrl != '' ">
            AND bind.`customs_declaration_url` = #{customsDeclarationUrl}
        </if>
        <if test="customsDeclarationNo != null and customsDeclarationNo != '' ">
            AND bind.`customs_declaration_no` = #{customsDeclarationNo}
        </if>
        <if test="waybillUrl != null and waybillUrl != '' ">
            AND bind.`waybill_url` = #{waybillUrl}
        </if>

        <if test="createTime != null and createTime != '' ">
            AND bind.`create_time` = #{createTime}
        </if>
        <if test="updateTime != null and updateTime != '' ">
            AND bind.`update_time` = #{updateTime}
        </if>

        <if test="createUserId != null and createUserId != '' ">
            AND bind.`create_user_id` = #{createUserId}
        </if>
        <if test="isDeleted != null and isDeleted != '' ">
            AND bind.`is_deleted` = #{waybillUrl}
        </if>
        <if test="status != null and status != '' ">
            AND bind.`status` = #{status}
        </if>
        <if test="waybillUrl != null and waybillUrl != '' ">
            AND bind.`waybill_url` = #{waybillUrl}
        </if>
        <choose>
            <when test=" container2 != null and container2 != ''  ">
                AND ( bind.`CONTAINER1` LIKE CONCAT('%', #{container1}, '%') OR bind.`CONTAINER2` LIKE CONCAT('%',
            #{container2}, '%') )
                -- 如果集装箱2不是null，就在两个集装箱中查询
            </when>
            <when test=" container2 == null ">
                <if test="container1 != null and container1 != '' ">
                    AND bind.`CONTAINER1` LIKE CONCAT('%', #{container1}, '%')
                </if>
            </when>
        </choose>

        <if test=" plateNo != null   and   plateNo != '' ">
            AND truck.PLATE_NO LIKE CONCAT('%', #{plateNo}, '%')
        </if>
        <choose>
            <when test="desc != null and desc == 1">
                ORDER BY updateTime DESC
            </when>
            <otherwise>
                ORDER BY updateTime ASC
            </otherwise>
        </choose>

        <if test="start != null and start != '' and  end != null and end != '' and start > 0 and end > start ">
            LIMIT ${start-1}, ${end-start+1}
        </if>
    </select>


    <select id="getPicByPicId" resultType="cn.jssgx.prealtruck.foundation.domain.model.truck.PicPath">
        SELECT
        ABBREVI_OSS_PATH abbreviOssPath,
        ORIGINAL_OSS_PATH originalOssPath
        FROM ts_pic
        WHERE PIC_ID = #{picId}
    </select>

    <select id="queryBindBase"
            parameterType="cn.jssgx.prealtruck.foundation.domain.model.eutrain.EutrainCustomsDeclarationBind"
            resultType="cn.jssgx.prealtruck.foundation.domain.model.eutrain.EutrainCustomsDeclarationBind">
        SELECT
        bind.`id` AS id,
        bind.`TASK_ID` AS taskId,
        bind.`gross_weights` AS grossWeights,
        bind.`net_weights` AS netWeights,
        bind.`send_Date` AS sendDate,
        bind.`trade_state` AS tradeState,
        bind.`customs_declaration_no` AS customsDeclarationNo,
        bind.`customs_declaration_url` AS customsDeclarationUrl,
        bind.`waybill_url` AS waybillUrl,
        bind.`create_time` AS createTime,
        bind.`update_time` AS updateTime,
        bind.`create_user_id` AS createUserId,
        bind.`is_deleted` AS isDeleted,
        bind.`delete_user_id` AS deleteUserId,
        bind.`status` AS status,
        bind.`CONTAINER1` AS container1,
        bind.`CONTAINER2` AS container2
        FROM `ts_eutrain_customs_declaration_bind` bind
        WHERE 1 = 1
        <if test="id != null and id != '' ">
            AND bind.`id` = #{id}
        </if>

        <if test="taskId != null and taskId != '' ">
            AND bind.`TASK_ID` = #{taskId}
        </if>
        <if test="grossWeights != null and grossWeights != '' ">
            AND bind.`gross_weights` = #{grossWeights}
        </if>
        <if test="netWeights != null and netWeights != '' ">
            AND bind.`net_weights` = #{netWeights}
        </if>
        <if test="sendDate != null and sendDate != '' ">
            AND bind.`send_Date` = #{sendDate}
        </if>
        <if test="customsDeclarationNo != null and customsDeclarationNo != '' ">
            AND bind.`customs_declaration_no` = #{customsDeclarationNo}
        </if>
        <if test="customsDeclarationUrl != null and customsDeclarationUrl != '' ">
            AND bind.`customs_declaration_url` = #{customsDeclarationUrl}
        </if>

        <if test="waybillUrl != null and waybillUrl != '' ">
            AND bind.`waybill_url` = #{waybillUrl}
        </if>

        <if test="createTime != null and createTime != '' ">
            AND bind.`create_time` = #{createTime}
        </if>
        <if test="updateTime != null and updateTime != '' ">
            AND bind.`update_time` = #{updateTime}
        </if>

        <if test="createUserId != null and createUserId != '' ">
            AND bind.`create_user_id` = #{createUserId}
        </if>
        <if test="isDeleted != null and isDeleted != '' ">
            AND bind.`is_deleted` = #{isDeleted}
        </if>
        <if test="deleteUserId != null and deleteUserId != '' ">
            AND bind.`delete_user_id` = #{deleteUserId}
        </if>
        <if test="status != null and status != '' ">
            AND bind.`status` = #{status}
        </if>
        <if test="container1 != null and container1 != '' ">
            AND bind.`CONTAINER1` = #{container1}
        </if>
        <if test="container2 != null and container2 != '' ">
            AND bind.`CONTAINER2` = #{container2}
        </if>


    </select>

    <select id="queryCountIncludeContainer"
            parameterType="java.util.List"
            resultType="java.lang.Integer">
        SELECT
        COUNT(1)
        FROM `ts_eutrain_customs_declaration_bind` bind
        WHERE 1=1
        <if test="containers != null and containers.size() > 0  ">
            AND (
            <foreach collection="containers" item="container" separator="OR">
                <if test="container != null and container != '' ">
                    bind.`CONTAINER1` = #{container} OR bind.`CONTAINER2` = #{container}
                </if>

            </foreach>
            )
        </if>
        AND bind.`is_deleted` = 1

    </select>

    <select id="queryTaskIDIsExist"
            resultType="java.lang.Integer">
        SELECT
        COUNT(1)
        FROM `ts_transportationtask_journey_info` journey
        WHERE 1=1
        AND journey.`TASK_ID` = #{TASK_ID}

    </select>


</mapper>