package com.tt1.simserver.modelo;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonTypeName;
import io.swagger.annotations.*;
import io.swagger.annotations.ApiModelProperty;
import jakarta.validation.Valid;
import jakarta.validation.constraints.*;

import java.util.Objects;



@JsonTypeName("SolicitudResponse")
@jakarta.annotation.Generated(value = "org.openapitools.codegen.languages.JavaJAXRSSpecServerCodegen", date = "2026-04-13T15:30:42.005536+02:00[Europe/Madrid]", comments = "Generator version: 7.4.0")
public class SolicitudResponse   {
  private @Valid Boolean done;
  private @Valid Integer tokenSolicitud;
  private @Valid String errorMessage;
  private @Valid Boolean data;

  /**
   **/
  public SolicitudResponse done(Boolean done) {
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
  public SolicitudResponse tokenSolicitud(Integer tokenSolicitud) {
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
  public SolicitudResponse errorMessage(String errorMessage) {
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
  public SolicitudResponse data(Boolean data) {
    this.data = data;
    return this;
  }

  
  @ApiModelProperty(value = "")
  @JsonProperty("data")
  public Boolean getData() {
    return data;
  }

  @JsonProperty("data")
  public void setData(Boolean data) {
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
    SolicitudResponse solicitudResponse = (SolicitudResponse) o;
    return Objects.equals(this.done, solicitudResponse.done) &&
        Objects.equals(this.tokenSolicitud, solicitudResponse.tokenSolicitud) &&
        Objects.equals(this.errorMessage, solicitudResponse.errorMessage) &&
        Objects.equals(this.data, solicitudResponse.data);
  }

  @Override
  public int hashCode() {
    return Objects.hash(done, tokenSolicitud, errorMessage, data);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class SolicitudResponse {\n");
    
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

