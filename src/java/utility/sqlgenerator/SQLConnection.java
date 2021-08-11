package utility.sqlgenerator;

import java.io.UnsupportedEncodingException;
import java.sql.*;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Locale;
import label.CoreLabel;
import utility.*;

public class SQLConnection {

    public static void main(String[] args) throws QException {
        try {

//            System.out.println("qafqaz");
//            connectionCreate();
        } catch (Exception e) {
            throw new QException(new Object() {
            }.getClass().getEnclosingClass().getName(),
                    new Object() {
                    }.getClass().getEnclosingMethod().getName(),
                    e);
        }
    }

//    public static void connectionCreate() throws QException {
//        try {
//
//            Class.forName("com.mysql.jdbc.Driver").newInstance();
//            Connection conn = DriverManager.getConnection("jdbc:mysql://localhost/test?"
//                    + "user=test_user&password=test1234");
//            conn.setAutoCommit(true);
//            Statement stmt = conn.createStatement();
//
//            //    ResultSet rset = stmt.executeQuery("INSERT INTO STUDENT_INFO(SURNAME,BIRTH_DAY,MODIFICATION_DATE,INSERT_DATE,BIRTH_PLACE,NAME,ID) VALUES ('lomush','19770506','20120606','20120202','Turkey','Kenan','11111')");
//            // stmt.execute("commit");
//            //    System.out.println("user :"+user+", parol:"+parol);
//            ResultSet rs = stmt.executeQuery("select * from us_users");
//            //stmt.execute("commit");
//            //Carrier qc= convertResultSetToCarrier(rs,"table");
//
//            //for (int i=0;i<)
//            //System.out.println(qc);
////            while (rs.next()) {
//////                System.out.println("sezon: " + rs.getString(1).toString());
////                System.out.println("qiymetler: " + rs.getString(2).toString());
////            }
//            //stmt.close();
////            System.out.println("Ok.");
//        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | SQLException e) {
////            throw throw new QException();
//            throw new QException(new Object() {
//            }.getClass().getEnclosingClass().getName(),
//                    new Object() {
//            }.getClass().getEnclosingMethod().getName(),
//                    e);
//        }
//    }
    public static void execInsertSql(String arg, String[] values) throws QException {
        execInsertSql(arg, CoreLabel.DB_PRIMARY, values);
    }

    public static void execInsertSql(String sqlQuery, String databaseNumber, String[] values) throws QException {
        try {
            Connection conn = SessionManager.getCurrentConnection();
            execInsertSql(sqlQuery, databaseNumber, values, conn);
        } catch (Exception e) {
            e.printStackTrace();
            throw new QException(new Object() {
            }.getClass().getEnclosingClass().getName(),
                    new Object() {
                    }.getClass().getEnclosingMethod().getName(),
                    e);
        }
//        System.out.println("Ok.");
    }

    public static void execInsertSql(String sqlQuery, String databaseNumber, String[] values,
            Connection connection) throws QException {
        try {
            Connection conn = SessionManager.getCurrentConnection();
            conn.setCatalog(SessionManager.getCurrentDomain());
            try (PreparedStatement stmt = conn.prepareStatement(sqlQuery)) {
                String line = "";
                for (int i = 0; i < values.length; i++) {
                    String st = values[i].trim();
                    stmt.setObject(i + 1, st);
                    line = line + i + "->" + st + ";";
                }
                QLogger.saveLogToFile(sqlQuery, line);
                stmt.executeUpdate();
            }
        } catch (Exception e) {
            throw new QException(new Object() {
            }.getClass().getEnclosingClass().getName(),
                    new Object() {
                    }.getClass().getEnclosingMethod().getName(),
                    e);
        }
    }

    private static void addValueToStatement(PreparedStatement stmt, int ind, String value, ArrayList array) {
        try {
            stmt.setObject(ind, value);
            array.add(value);
        } catch (SQLException ex) {
            new QException(new Object() {
            }.getClass().getEnclosingClass().getName(),
                    new Object() {
                    }.getClass().getEnclosingMethod().getName(), ex);
        }
    }

    public static Carrier execSelectSql(String arg, String tableName, String databaseNumber1, ArrayList values) throws QException {

        Carrier qc1 = new Carrier();
        ArrayList logLine = new ArrayList();
        try {
            Connection conn = SessionManager.getCurrentConnection();
            conn.setCatalog(SessionManager.getCurrentDomain());
            PreparedStatement stmt = conn.prepareStatement(arg);

            int idx = 1;
            String line = "";
            String query = arg;
            for (int i = 0; i < values.size(); i++) {
                if (!values.get(i).toString().trim().equals("")) {
                    String val = values.get(i).toString().trim();
//                    val = val.trim().
//                            replace("<", "&lt;").replace(">", "&gt;")
//                            .replace("'", "&quot;").replace("\"", "&quot;")
//                            .replace("/", "&#x2F;");
                    addValueToStatement(stmt, idx++, val, logLine);
                }
            }
            QLogger.saveLogToFile(stmt.toString(), logLine);
            try (ResultSet rs = stmt.executeQuery()) {
                qc1 = convertResultSetToCarrier(rs, tableName);
            }
            stmt.close();
        } catch (SQLException | QException e) {
            throw new QException(new Object() {
            }.getClass().getEnclosingClass().getName(),
                    new Object() {
                    }.getClass().getEnclosingMethod().getName(),
                    e);
        }
        return qc1;
    }

    public static Carrier execSelectSql(String arg, String tableName, String databaseNumber, ArrayList values, Connection conn1) throws QException {
        Carrier qc1 = new Carrier();
        ArrayList logLine = new ArrayList();
        try {
            Connection conn = SessionManager.getCurrentConnection();
            PreparedStatement stmt = conn.prepareStatement(arg);
            int idx = 1;
            String line = "";
            String query = arg;
            for (int i = 0; i < values.size(); i++) {
                if (!values.get(i).toString().trim().equals("")) {
                    String val = values.get(i).toString().trim();
//                    val = val.trim().
//                            replace("<", "&lt;").replace(">", "&gt;")
//                            .replace("'", "&quot;").replace("\"", "&quot;")
//                            .replace("/", "&#x2F;");
//                    query = query.replace("?", "'"+val+"'");
                    addValueToStatement(stmt, idx++, val, logLine);
                }
            }
            QLogger.saveLogToFile(query, logLine);
            try (ResultSet rs = stmt.executeQuery()) {
                qc1 = convertResultSetToCarrier(rs, tableName);
            }
            stmt.close();
        } catch (SQLException | QException e) {
            throw new QException(new Object() {
            }.getClass().getEnclosingClass().getName(),
                    new Object() {
                    }.getClass().getEnclosingMethod().getName(),
                    e);
        }
        return qc1;
    }

    public static void execUpdateSql(String arg, ArrayList valueList) throws QException {
        execUpdateSql(arg, CoreLabel.DB_PRIMARY, valueList);
    }

    public static void execUpdateSql(String arg, String databaseNumber, ArrayList valueList) throws QException {
        try {
            Connection conn = SessionManager.getCurrentConnection();
            conn.setCatalog(SessionManager.getCurrentDomain());
            PreparedStatement stmt = conn.prepareStatement(arg);

            String line = "";
            for (int i = 0; i < valueList.size(); i++) {
                String value = valueList.get(i).toString().trim();
//                value = value.trim().
//                        replace("<", "&lt;").replace(">", "&gt;")
//                        .replace("'", "&quot;").replace("\"", "&quot;")
//                        .replace("/", "&#x2F;");
                stmt.setObject(i + 1, value);
                line = line + i + "->" + value + ";";
            }

            QLogger.saveLogToFile(stmt.toString(), "");

            stmt.executeUpdate();
            stmt.close();

        } catch (Exception e) {

            throw new QException(new Object() {
            }.getClass().getEnclosingClass().getName(),
                    new Object() {
                    }.getClass().getEnclosingMethod().getName(),
                    e);
        }
//        System.out.println("Ok.");
    }

    public static void execUpdateSql(String arg, String databaseNumber, ArrayList valueList,
            Connection conn1) throws QException {
        try {

            Connection conn = SessionManager.getCurrentConnection();
            PreparedStatement stmt = conn.prepareStatement(arg);
            String line = "";
            for (int i = 0; i < valueList.size(); i++) {
                String value = valueList.get(i).toString().trim();
//                 value = value.trim().
//                        replace("<", "&lt;").replace(">", "&gt;")
//                        .replace("'", "&quot;").replace("\"", "&quot;")
//                        .replace("/", "&#x2F;");
                stmt.setObject(i + 1, value);
                line = line + i + "->" + value + ";";
            }

            QLogger.saveLogToFile(arg, line);
            stmt.executeUpdate();
            stmt.close();

        } catch (Exception e) {
            throw new QException(new Object() {
            }.getClass().getEnclosingClass().getName(),
                    new Object() {
                    }.getClass().getEnclosingMethod().getName(),
                    e);
        }
    }

    public static Carrier execDeleteSql(String arg) throws Exception {
        try {
            Connection conn = SessionManager.getCurrentConnection();
            conn.setCatalog(SessionManager.getCurrentDomain());
            Statement stmt = conn.createStatement();
            QLogger.saveLogToFile(arg);
            stmt.execute(arg);
            stmt.close();
        } catch (Exception e) {
            throw e;
        }
//        System.out.println("Ok.");
        return null;
    }

    public static void execBlobFieldQuery(String arg, String value) throws QException, SQLException, UnsupportedEncodingException {
        try {
            Connection conn = SessionManager.getCurrentConnection();
            PreparedStatement stmt = conn.prepareStatement(arg);

//            InputStream stream = new ByteArrayInputStream(value.getBytes(StandardCharsets.UTF_8));
            byte[] b = value.getBytes("UTF-8");

            stmt.setBytes(1, b);
            stmt.executeUpdate();
            stmt.close();

        } catch (Exception e) {
            throw new QException(new Object() {
            }.getClass().getEnclosingClass().getName(),
                    new Object() {
                    }.getClass().getEnclosingMethod().getName(),
                    e);
        }
    }

    public static String selectBlobFieldQuery(String arg) throws QException, SQLException, UnsupportedEncodingException {
        String res = "";
        try {
            Connection conn = SessionManager.getCurrentConnection();
            PreparedStatement stmt = conn.prepareStatement(arg);

            try (ResultSet rs = stmt.executeQuery()) {
                int row = 0;
                while (rs.next()) {
                    byte bytes[] = rs.getBytes(1);
                    res = new String(bytes, "UTF-8");

                }
            }
            stmt.close();
        } catch (Exception e) {
            throw new QException(new Object() {
            }.getClass().getEnclosingClass().getName(),
                    new Object() {
                    }.getClass().getEnclosingMethod().getName(),
                    e);
        }
        return res;
    }

    private static String[] getColumnNames(ResultSet rs) throws QException {
        try {
            ResultSetMetaData rsmd = rs.getMetaData();
            int columnCount = rsmd.getColumnCount();
            String[] columnNames = new String[columnCount];
            // The column count starts from 1
            for (int i = 1; i < columnCount + 1; i++) {
                columnNames[i - 1] = rsmd.getColumnName(i);
            }
            return columnNames;
        } catch (Exception e) {
            throw new QException(new Object() {
            }.getClass().getEnclosingClass().getName(),
                    new Object() {
                    }.getClass().getEnclosingMethod().getName(),
                    e);

        }

    }

    private static String getTableName(ResultSet rs) throws QException {
        String cc = "";
        try {
            rs.next();
            ResultSetMetaData rsmd = rs.getMetaData();
            int columnCount = rsmd.getColumnCount();
            String[] columnNames = new String[columnCount];
            cc = rsmd.getTableName(1) + rsmd.getTableName(2);
        } catch (Exception e) {
            throw new QException(new Object() {
            }.getClass().getEnclosingClass().getName(),
                    new Object() {
                    }.getClass().getEnclosingMethod().getName(),
                    e);
        }

        return "table";
    }

    public static Carrier convertResultSetToCarrier(ResultSet rs, String tableName) throws QException {
        String[] colNames = getColumnNames(rs);

        String cols = "";
        for (int i = 0; i < colNames.length; i++) {
            cols +=  convertTableFieldNameToEntityfieldName(colNames[i]);
            cols += (i < colNames.length - 1) ? "," : "";
        }
        

        Carrier qCarry1 = new Carrier();
        qCarry1.setSelectedField(cols);
        
        try {
            int row = 0;

            while (rs.next()) {
                for (int i = 0; i <= colNames.length - 1; i++) {

                    String vl = rs.getString(colNames[i]) == null ? "" : rs.getString(colNames[i]).trim();
                    String fieldName = convertTableFieldNameToEntityfieldName(colNames[i]);
                    qCarry1.setValue(tableName, row, fieldName, vl);
//                    qCarry1.setValue(CoreLabel.RESULT_SET, row, fieldName, vl);
                }
                row++;
            }
        } catch (SQLException | QException e) {
            throw new QException(new Object() {
            }.getClass().getEnclosingClass().getName(),
                    new Object() {
                    }.getClass().getEnclosingMethod().getName(),
                    e);
        }
        return qCarry1;
    }

    private static Carrier convertResultSetToCarrierBoth(ResultSet rs, String tableName) throws QException {
        String[] colNames = getColumnNames(rs);
        //String 	tableName = getTableName(rs);
        Carrier qCarry1 = new Carrier();
        try {
            int row = 0;
            while (rs.next()) {
                for (int i = 0; i <= colNames.length - 1; i++) {
                    qCarry1.setValue(tableName, row, i, rs.getString(colNames[i]).trim());
                    qCarry1.setValue(tableName, row, convertTableFieldNameToEntityfieldName(colNames[i]), rs.getString(colNames[i]).trim());
                }
                row++;
            }
        } catch (SQLException | QException e) {
            throw new QException(new Object() {
            }.getClass().getEnclosingClass().getName(),
                    new Object() {
                    }.getClass().getEnclosingMethod().getName(),
                    e);
        }
        return qCarry1;
    }

    static String convertTableFieldNameToEntityfieldName(String arg) {
        String UNDERSCORE = "_";
        String st[] = arg.split(UNDERSCORE);
        String res = st[0].toLowerCase(Locale.ENGLISH);
        for (int i = 1; i <= st.length - 1; i++) {
            res = res + st[i].substring(0, 1).toUpperCase(Locale.ENGLISH) + st[i].substring(1, st[i].length()).toLowerCase(Locale.ENGLISH);
        }
        return res;
    }

}
