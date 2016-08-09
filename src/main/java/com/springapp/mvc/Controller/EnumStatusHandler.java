package com.springapp.mvc.Controller;

import com.springapp.mvc.Model.Account;
import com.springapp.mvc.Model.AccountType;
import com.springapp.mvc.Model.IntEnum;
import org.apache.ibatis.type.BaseTypeHandler;
import org.apache.ibatis.type.JdbcType;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by Administrator on 2015/11/8.
 * mybatis存储和读取enum类使用
 */
public class EnumStatusHandler<E extends Enum<E> & IntEnum<E>> extends BaseTypeHandler<IntEnum> {
    private Class<IntEnum> type;

    public EnumStatusHandler(Class<IntEnum> type){
        if(type == null) {
            throw new IllegalArgumentException("Type argument cannot be null");
        }
        this.type = type;
    }
    private IntEnum convert(int status) {
        IntEnum[] enums = type.getEnumConstants();
        for (IntEnum em : enums){
            if (em.getIntValue() == status){
                return em;
            }
        }
        return null;
    }

    @Override
    public void setNonNullParameter(PreparedStatement ps, int i, IntEnum parameter,
                                    JdbcType jdbcType) throws SQLException{
        // baseTypeHandler已经帮我们做了parameter的null判断
        ps.setInt(i, parameter.getIntValue());
    }

    @Override
    public IntEnum getNullableResult(ResultSet rs, String columnName)
            throws SQLException {
        // 根据数据库存储类型决定获取类型，本例子中数据库中存放INT类型
        return convert(rs.getInt(columnName));
    }

    @Override
    public IntEnum getNullableResult(ResultSet rs, int columnIndex)
            throws SQLException {
        // 根据数据库存储类型决定获取类型，本例子中数据库中存放INT类型
        return convert(rs.getInt(columnIndex));
    }

    @Override
    public IntEnum getNullableResult(CallableStatement cs, int columnIndex)
            throws SQLException {
        // 根据数据库存储类型决定获取类型，本例子中数据库中存放INT类型
        return convert(cs.getInt(columnIndex));
    }
}
