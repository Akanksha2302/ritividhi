    
package com.dev.ritividhi.models;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Data
@Document(collection = "puja")
public class Puja {

		@Id
	    private String pujaId;
	    private String name;
	    private String significance;
	   
}
