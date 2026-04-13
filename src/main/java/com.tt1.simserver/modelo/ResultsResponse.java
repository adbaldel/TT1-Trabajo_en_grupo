package com.tt1.simserver.modelo;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonTypeName;
import io.swagger.annotations.*;
import io.swagger.annotations.ApiModelProperty;
import jakarta.validation.Valid;
import jakarta.validation.constraints.*;

import java.util.Objects;



@JsonTypeName("ResultsResponse")
@jakarta.annotation.Generated(value = "org.openapitools.codegen.languages.JavaJAXRSSpecServerCodegen", date = "2026-04-13T15:30:42.005536+02:00[Europe/Madrid]", comments = "Generator version: 7.4.0")
public class ResultsResponse   {
  private @Valid Boolean done;
  private @Valid Integer tokenSolicitud;
  private @Valid String errorMessage;
  private @Valid String data;

  /**
   **/
  public ResultsResponse done(Boolean done) {
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
  public ResultsResponse tokenSolicitud(Integer tokenSolicitud) {
    this.tokenSolicitud = tokenSolicitud;
    return this;
  }

  
  @ApiModelProperty(value = "")
  @JsonProperty("tokenSolicitud")
  public Integer getTokenSolicitud() {
    return tokenSolicitud;
  }

  @JsonProperty("tokenSolicitud")
  public void setTokenSolicitud(Integer tokenSolicitud) {
    this.tokenSolicitud = tokenSolicitud;
  }

  /**
   **/
  public ResultsResponse errorMessage(String errorMessage) {
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

  /**
   **/
  public ResultsResponse data(String data) {
    this.data = data;
    return this;
  }

  
  @ApiModelProperty(value = "")
  @JsonProperty("data")
  public String getData() {
    return data;
  }

  @JsonProperty("data")
  public void setData(String data) {
    this.data = data;
  }


  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    ResultsResponse resultsResponse = (ResultsResponse) o;
    return Objects.equals(this.done, resultsResponse.done) &&
        Objects.equals(this.tokenSolicitud, resultsResponse.tokenSolicitud) &&
        Objects.equals(this.errorMessage, resultsResponse.errorMessage) &&
        Objects.equals(this.data, resultsResponse.data);
  }

  @Override
  public int hashCode() {
    return Objects.hash(done, tokenSolicitud, errorMessage, data);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class ResultsResponse {\n");
    
    sb.append("    done: ").append(toIndentedString(done)).append("\n");
    sb.append("    tokenSolicitud: ").append(toIndentedString(tokenSolicitud)).append("\n");
    sb.append("    errorMessage: ").append(toIndentedString(errorMessage)).append("\n");
    sb.append("    data: ").append(toIndentedString(data)).append("\n");
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

