package com.twitter.finagle.ssl.client

import com.twitter.finagle.Address
import com.twitter.finagle.ssl.Engine
import javax.net.ssl.SSLContext

/**
 * This class provides an ability to use an initialized supplied
 * [[javax.net.ssl.SSLContext]] as the basis for creating [[Engine Engines]].
 */
private[ssl] case class SslContextClientEngineFactory(
    sslContext: SSLContext)
  extends SslClientEngineFactory {

  /**
   * Creates a new [[Engine]] based on an [[Address]] and an [[SslClientConfiguration]]
   * using the supplied [[javax.net.ssl.SSLContext]].
   *
   * @param address A physical address which potentially includes metadata.
   *
   * @param config A collection of parameters which the engine factory should
   * consider when creating the TLS client [[Engine]].
   *
   * @note [[KeyCredentials]] other than Unspecified are not supported.
   * @note [[TrustCredentials]] other than Unspecified are not supported.
   * @note [[ApplicationProtocols]] other than Unspecified are not supported.
   */
  def mkEngine(address: Address, config: SslClientConfiguration): Engine = {
    SslClientEngineFactory.checkKeyCredentialsNotSupported(
      "SslContextClientEngineFactory", config)
    SslClientEngineFactory.checkTrustCredentialsNotSupported(
      "SslContextClientEngineFactory", config)
    SslClientEngineFactory.checkApplicationProtocolsNotSupported(
      "SslContextClientEngineFactory", config)

    val engine = SslClientEngineFactory.createEngine(sslContext, address, config)
    SslClientEngineFactory.configureEngine(engine, config)
    engine
  }

}