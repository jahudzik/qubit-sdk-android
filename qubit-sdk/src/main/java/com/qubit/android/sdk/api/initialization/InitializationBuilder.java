package com.qubit.android.sdk.api.initialization;

import android.content.Context;
import com.qubit.android.sdk.api.logging.QBLogLevel;
import com.qubit.android.sdk.internal.SDK;
import com.qubit.android.sdk.internal.logging.QBLogger;

public class InitializationBuilder {

  public interface SdkConsumer {
    void accept(SDK sdk);
  }

  private final SdkConsumer sdkConsumer;
  private Context appContext;
  private String trackingId;
  private QBLogLevel logLevel;

  public InitializationBuilder(SdkConsumer sdkConsumer) {
    this.sdkConsumer = sdkConsumer;
  }

  public ContextSetInitializationBuilder inAppContext(Context context) {
    this.appContext = context;
    return new ContextSetInitializationBuilder();
  }


  public class ContextSetInitializationBuilder {
    public MandatoryPropertiesSetInitializationBuilder withTrackingId(String trackingId) {
      InitializationBuilder.this.trackingId = trackingId;
      return new MandatoryPropertiesSetInitializationBuilder();
    }
  }


  public class MandatoryPropertiesSetInitializationBuilder {

    public MandatoryPropertiesSetInitializationBuilder withLogLevel(QBLogLevel logLevel) {
      InitializationBuilder.this.logLevel = logLevel;
      return this;
    }

    public void start() {
      QBLogger.logLevel = logLevel;
      SDK sdk = new SDK(appContext, trackingId);
      sdk.start();
      sdkConsumer.accept(sdk);
    }
  }

}
