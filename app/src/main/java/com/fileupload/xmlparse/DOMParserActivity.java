package com.fileupload.xmlparse;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.fileupload.R;

/**
 * Created by SONI on 10/3/2018.
 */

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import android.app.Activity;
import android.content.res.AssetManager;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

public class DOMParserActivity extends Activity implements OnClickListener,
        OnItemClickListener {
    Button button;
    ListView listView;
    List<Employee> employees = null;

    // XML node names
    static final String ATTR_ID = "id";
    static final String NODE_EMP = "employee";
    static final String NODE_NAME = "name";
    static final String NODE_DEPT = "department";
    static final String NODE_TYPE = "type";
    static final String NODE_EMAIL = "email";
    static final String NODE_ADDR = "address";
    static final String NODE_LINE1 = "line1";
    static final String NODE_CITY = "city";
    static final String NODE_STATE = "state";
    static final String NODE_ZIP = "zipcode";

    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

        findViewsById();
        button.setOnClickListener(this);
    }

    private void findViewsById() {
        button = (Button) findViewById(R.id.button);
        listView = (ListView) findViewById(R.id.employeeList);
    }

    public void onClick(View view) {
        XMLDOMParser parser = new XMLDOMParser();
        AssetManager manager = getAssets();
        InputStream stream;
        try {
            stream = manager.open("employees.xml");
            Document doc = parser.getDocument(stream);

            // Get elements by name employee
            NodeList nodeList = doc.getElementsByTagName(NODE_EMP);
            employees = new ArrayList<Employee>();
            /*
             * for each <employee> element get text of name, department, type
             * and email and line1, city, state, zipcode of <address> element.
             */
            for (int i = 0; i < nodeList.getLength(); i++) {
                Element e = (Element) nodeList.item(i);

                Employee employee = new Employee();

                employee.setId(Integer.parseInt(e.getAttribute(ATTR_ID)));
                employee.setName(parser.getValue(e, NODE_NAME));
                employee.setDepartment(parser.getValue(e, NODE_DEPT));
                employee.setType(parser.getValue(e, NODE_TYPE));
                employee.setEmail(parser.getValue(e, NODE_EMAIL));

                Address address = new Address();
                address.setLine(parser.getValue(e, NODE_LINE1));
                address.setCity(parser.getValue(e, NODE_CITY));
                address.setState(parser.getValue(e, NODE_STATE));
                address.setZipcode(Long.parseLong(parser.getValue(e, NODE_ZIP)));
                employee.setAddress(address);

                employees.add(employee);
            }
            displayOutput();

        } catch (IOException e1) {
            e1.printStackTrace();
        }
    }

    private void displayOutput() {
        ArrayAdapter<Employee> adapter = new ArrayAdapter<Employee>(this,
                R.layout.list_item, employees);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(this);
    }

    public void onItemClick(AdapterView<?> parent, View view, int position,
                            long id) {
        Employee employee = employees.get(position);
        Toast.makeText(parent.getContext(), employee.getDetails(),
                Toast.LENGTH_LONG).show();
    }
}