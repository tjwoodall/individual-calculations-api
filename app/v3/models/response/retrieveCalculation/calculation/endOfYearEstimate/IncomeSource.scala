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

package v3.models.response.retrieveCalculation.calculation.endOfYearEstimate

import play.api.libs.json.{JsPath, Json, OWrites, Reads}
import play.api.libs.functional.syntax._
import v3.models.response.common.IncomeSourceType

case class IncomeSource(
    incomeSourceId: Option[String],
    incomeSourceType: IncomeSourceType,
    incomeSourceName: Option[String],
    taxableIncome: BigInt,
    finalised: Option[Boolean]
)

object IncomeSource {
  implicit val writes: OWrites[IncomeSource] = Json.writes[IncomeSource]

  implicit val reads: Reads[IncomeSource] = (
    (JsPath \ "incomeSourceId").readNullable[String] and
      (JsPath \ "incomeSourceType").read[IncomeSourceType] and
      (JsPath \ "incomeSourceName").readNullable[String] and
      (JsPath \ "taxableIncome").read[BigInt] and
      (JsPath \ "finalised").readNullable[Boolean]
  )(IncomeSource.apply _)

}
