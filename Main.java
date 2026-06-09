import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;

public class Main extends JFrame implements ActionListener {
    JTextField tId, tName, tDept, tCgpa;
    JTextArea area;
    JButton bAdd, bView, bUpdate, bDelete, bSearch, bClear;
    
    Main() {
        setTitle("Student Management System - JDBC Project");
        setSize(600, 450);
        setLayout(null);
        
        JLabel l1 = new JLabel("ID:"); l1.setBounds(20,20,80,30); add(l1);
        tId = new JTextField(); tId.setBounds(120,20,150,30); add(tId);
        
        JLabel l2 = new JLabel("Name:"); l2.setBounds(20,60,80,30); add(l2);
        tName = new JTextField(); tName.setBounds(120,60,150,30); add(tName);
        
        JLabel l3 = new JLabel("Dept:"); l3.setBounds(20,100,80,30); add(l3);
        tDept = new JTextField(); tDept.setBounds(120,100,150,30); add(tDept);
        
        JLabel l4 = new JLabel("CGPA:"); l4.setBounds(20,140,80,30); add(l4);
        tCgpa = new JTextField(); tCgpa.setBounds(120,140,150,30); add(tCgpa);
        
        bAdd = new JButton("Add"); bAdd.setBounds(300,20,100,30); add(bAdd);
        bView = new JButton("View All"); bView.setBounds(300,60,100,30); add(bView);
        bUpdate = new JButton("Update"); bUpdate.setBounds(300,100,100,30); add(bUpdate);
        bDelete = new JButton("Delete"); bDelete.setBounds(300,140,100,30); add(bDelete);
        bSearch = new JButton("Search"); bSearch.setBounds(420,20,100,30); add(bSearch);
        bClear = new JButton("Clear"); bClear.setBounds(420,60,100,30); add(bClear);
        
        area = new JTextArea();
        JScrollPane sp = new JScrollPane(area);
        sp.setBounds(20,190,550,200);
        add(sp);
        
        bAdd.addActionListener(this);
        bView.addActionListener(this);
        bUpdate.addActionListener(this);
        bDelete.addActionListener(this);
        bSearch.addActionListener(this);
        bClear.addActionListener(this);
        
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
    }
    
    public void actionPerformed(ActionEvent e) {
        if(e.getSource() == bAdd) addStudent();
        if(e.getSource() == bView) viewStudents();
        if(e.getSource() == bUpdate) updateStudent();
        if(e.getSource() == bDelete) deleteStudent();
        if(e.getSource() == bSearch) searchStudent();
        if(e.getSource() == bClear) clearFields();
    }
    
    void addStudent() {
        try {
            Connection con = DBConnection.getConnection();
            PreparedStatement ps = con.prepareStatement("INSERT INTO students(name, dept, cgpa) VALUES(?,?,?)");
            ps.setString(1, tName.getText());
            ps.setString(2, tDept.getText());
            ps.setDouble(3, Double.parseDouble(tCgpa.getText()));
            ps.executeUpdate();
            JOptionPane.showMessageDialog(this, "Student Added Successfully");
            con.close();
            clearFields();
        } catch(Exception ex) { JOptionPane.showMessageDialog(this, ex); }
    }
    
    void viewStudents() {
        try {
            Connection con = DBConnection.getConnection();
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery("SELECT * FROM students");
            area.setText("ID\tName\tDept\tCGPA\n----------------------------------------\n");
            while(rs.next()) {
                area.append(rs.getInt(1) + "\t" + rs.getString(2) + "\t\t" + 
                           rs.getString(3) + "\t" + rs.getDouble(4) + "\n");
            }
            con.close();
        } catch(Exception ex) { JOptionPane.showMessageDialog(this, ex); }
    }
    
    void updateStudent() {
        try {
            Connection con = DBConnection.getConnection();
            PreparedStatement ps = con.prepareStatement("UPDATE students SET name=?, dept=?, cgpa=? WHERE id=?");
            ps.setString(1, tName.getText());
            ps.setString(2, tDept.getText());
            ps.setDouble(3, Double.parseDouble(tCgpa.getText()));
            ps.setInt(4, Integer.parseInt(tId.getText()));
            ps.executeUpdate();
            JOptionPane.showMessageDialog(this, "Updated Successfully");
            con.close();
        } catch(Exception ex) { JOptionPane.showMessageDialog(this, ex); }
    }
    
    void deleteStudent() {
        try {
            Connection con = DBConnection.getConnection();
            PreparedStatement ps = con.prepareStatement("DELETE FROM students WHERE id=?");
            ps.setInt(1, Integer.parseInt(tId.getText()));
            ps.executeUpdate();
            JOptionPane.showMessageDialog(this, "Deleted Successfully");
            con.close();
            clearFields();
        } catch(Exception ex) { JOptionPane.showMessageDialog(this, ex); }
    }
    
    void searchStudent() {
        try {
            Connection con = DBConnection.getConnection();
            PreparedStatement ps = con.prepareStatement("SELECT * FROM students WHERE id=?");
            ps.setInt(1, Integer.parseInt(tId.getText()));
            ResultSet rs = ps.executeQuery();
            if(rs.next()) {
                tName.setText(rs.getString(2));
                tDept.setText(rs.getString(3));
                tCgpa.setText(String.valueOf(rs.getDouble(4)));
            } else {
                JOptionPane.showMessageDialog(this, "ID Not Found");
            }
            con.close();
        } catch(Exception ex) { JOptionPane.showMessageDialog(this, ex); }
    }
    
    void clearFields() {
        tId.setText(""); tName.setText(""); tDept.setText(""); tCgpa.setText("");
    }
    
    public static void main(String[] args) {
        new Main();
    }
}