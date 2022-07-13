package me.kofesst.android.shoppinglist.domain.usecases.validation

class ValidateForIntRange {
    operator fun invoke(value: Int, range: IntRange) = if (range.contains(value)) {
        ValidationResult.Success
    } else {
        ValidationResult.NotInRange
    }
}