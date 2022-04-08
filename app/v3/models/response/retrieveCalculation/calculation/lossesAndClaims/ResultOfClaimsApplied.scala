/*
 * Copyright 2022 HM Revenue & Customs
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package v3.models.response.retrieveCalculation.calculation.lossesAndClaims

import play.api.libs.json.{JsPath, Json, OWrites, Reads}
import v2.models.domain.DownstreamTaxYear
import v3.models.response.common.{IncomeSourceType, LossType}

case class ResultOfClaimsApplied(
    claimId: Option[String],
    originatingClaimId: Option[String],
    incomeSourceId: String,
    incomeSourceType: IncomeSourceType,
    taxYearClaimMade: String,
    claimType: String,
    mtdLoss: Option[Boolean],
    taxYearLossIncurred: String,
    lossAmountUsed: BigInt,
    remainingLossValue: BigInt,
    lossType: Option[LossType]
)

object ResultOfClaimsApplied {
  implicit val writes: OWrites[ResultOfClaimsApplied] = Json.writes[ResultOfClaimsApplied]

  implicit val reads: Reads[ResultOfClaimsApplied] = for {
    claimId            <- (JsPath \ "claimId").readNullable[String]
    originatingClaimId <- (JsPath \ "originatingClaimId").readNullable[String]
    incomeSourceId     <- (JsPath \ "incomeSourceId").read[String]

    validIncomeSourceTypes = Seq(
      IncomeSourceType.`self-employment`,
      IncomeSourceType.`uk-property-non-fhl`,
      IncomeSourceType.`foreign-property-fhl-eea`,
      IncomeSourceType.`uk-property-fhl`,
      IncomeSourceType.`foreign-property`
    )
    incomeSourceType <- (JsPath \ "incomeSourceType").read[IncomeSourceType].filter(validIncomeSourceTypes.contains(_))

    taxYearClaimMade    <- (JsPath \ "taxYearClaimMade").read[Int].map(DownstreamTaxYear.fromDownstreamIntToString)
    claimType           <- (JsPath \ "claimType").read[String]
    mtdLoss             <- (JsPath \ "mtdLoss").readNullable[Boolean]
    taxYearLossIncurred <- (JsPath \ "taxYearLossIncurred").read[Int].map(DownstreamTaxYear.fromDownstreamIntToString)
    lossAmountUsed      <- (JsPath \ "lossAmountUsed").read[BigInt]
    remainingLossValue  <- (JsPath \ "remainingLossValue").read[BigInt]
    lossType            <- (JsPath \ "lossType").readNullable[LossType]

  } yield {
    ResultOfClaimsApplied(
      claimId = claimId,
      originatingClaimId = originatingClaimId,
      incomeSourceId = incomeSourceId,
      incomeSourceType = incomeSourceType,
      taxYearClaimMade = taxYearClaimMade,
      claimType = claimType,
      mtdLoss = mtdLoss,
      taxYearLossIncurred = taxYearLossIncurred,
      lossAmountUsed = lossAmountUsed,
      remainingLossValue = remainingLossValue,
      lossType = lossType
    )
  }

}
