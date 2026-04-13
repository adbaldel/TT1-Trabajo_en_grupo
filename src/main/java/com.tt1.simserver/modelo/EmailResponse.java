package com.tt1.simserver.modelo;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonTypeName;
import io.swagger.annotations.*;
import io.swagger.annotations.ApiModelProperty;
import jakarta.validation.Valid;
import jakarta.validation.constraints.*;

import java.util.Objects;



@JsonTypeName("EmailResponse")
@jakarta.annotation.Generated(value = "org.openapitools.codegen.languages.JavaJAXRSSpecServerCodegen", date = "2026-04-13T15:30:42.005536+02:00[Europe/Madrid]", comments = "Generator version: 7.4.0")
public class EmailResponse   {
  private @Valid Boolean done;
  private @Valid String errorMessage;

  /**
   **/
  public EmailResponse done(Boolean done) {
    this.done = done;
    return this;
  }

  
  @ApiModelProperty(value = "")
  @JsonProperty("done")
  public Boolean getDone() {
    return done;
  }

  @JsonProperty("done")
  public void setDone(Boolean done) {
    this.done = done;
  }

  /**
   **/
  public EmailResponse errorMessage(String errorMessage) {
    this.errorMessage = errorMessage;
    return this;
  }

  
  @ApiModelProperty(value = "")
  @JsonProperty("errorMessage")
  public String getErrorMessage() {
    return errorMessage;
  }

  @JsonProperty("errorMessage")
  public void setErrorMessage(String errorMessage) {
    this.errorMessage = errorMessage;
  }


  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    EmailResponse emailResponse = (EmailResponse) o;
    return Objects.equals(this.done, emailResponse.done) &&
        Objects.equals(this.errorMessage, emailResponse.errorMessage);
  }

  @Override
  public int hashCode() {
    return Objects.hash(done, errorMessage);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class EmailResponse {\n");
    
    sb.append("    done: ").append(toIndentedString(done)).append("\n");
    sb.append("    errorMessage: ").append(toIndentedString(errorMessage)).append("\n");
    sb.append("}");
    return sb.toString();
  }

  /**
   * Convert the given object to string with each line indented by 4 spaces
   * (except the first line).
   */
  private String toIndentedString(Object o) {
    if (o == null) {
      return "null";
    }
    return o.toString().replace("\n", "\n    ");
  }


}

